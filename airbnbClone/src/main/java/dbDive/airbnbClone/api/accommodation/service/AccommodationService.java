package dbDive.airbnbClone.api.accommodation.service;

import com.amazonaws.services.s3.AmazonS3;
import dbDive.airbnbClone.api.accommodation.dto.AccommodationDataDto;
import dbDive.airbnbClone.api.accommodation.dto.request.AccommodationDto;
import dbDive.airbnbClone.api.accommodation.dto.request.AccommodationEditDto;
import dbDive.airbnbClone.api.accommodation.dto.request.SearchRequest;
import dbDive.airbnbClone.api.accommodation.dto.response.DetailAcmdResponse;
import dbDive.airbnbClone.api.accommodation.dto.response.SearchResponse;
import dbDive.airbnbClone.entity.accommodation.Accommodation;
import dbDive.airbnbClone.entity.accommodation.AcmdImage;
import dbDive.airbnbClone.repository.accommodation.AccommodationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final S3Service s3Service;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public SearchResponse search(Pageable pageable, SearchRequest request) {
        PageImpl<AccommodationDataDto> accommodations = accommodationRepository.search(pageable,request);
        return new SearchResponse(accommodations);
    }

    public DetailAcmdResponse detail(Long accommodationId) {
        return accommodationRepository.findAccommodation(accommodationId);
    }

    public Accommodation saveAccommodation(AccommodationDto dto,
                                           List<MultipartFile> images) {
        // 빌더패턴, 정적 팩토리 메서드
        Accommodation accommodation = new Accommodation(dto.getMainAddress(),  dto.getPrice(), dto.getDetailAddress(), dto.getAcmdName(), dto.getAcmdDescription(), dto.getGuest(), dto.getBedroom(), dto.getBed(), dto.getBathroom());
        List<AcmdImage> imageEntities = new ArrayList<>();

        for (MultipartFile imageFile : images) {
            String imgKey = s3Service.uploadFile(imageFile);
            String imageUrl = amazonS3.getUrl(bucketName, imgKey).toExternalForm();
            AcmdImage acmdImage = new AcmdImage(imageUrl, imgKey);
            accommodation.addImage(acmdImage);
        }

        return accommodationRepository.save(accommodation);
    }

    public Accommodation editAccommodation(Long accommodationId,
                                           AccommodationEditDto dto,
                                           List<MultipartFile> newImages) {

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 숙소가 없습니다 Id : " + accommodationId));

        accommodation.updateAccommodationDetails(dto.getBed(), dto.getBedroom(), dto.getBathroom(),
                dto.getGuest(), dto.getAcmdName(), dto.getAcmdDescription(), dto.getPrice());

        if (newImages != null && !newImages.isEmpty()) {
            for (MultipartFile newImageFile : newImages) {
                String newImgKey = s3Service.uploadFile(newImageFile);
                String newImageUrl = amazonS3.getUrl(bucketName, newImgKey).toExternalForm();
                AcmdImage newAcmdImage = new AcmdImage(newImageUrl, newImgKey);
                accommodation.addImage(newAcmdImage);
            }
        }

        if (dto.getDeleteImageKey() != null && !dto.getDeleteImageKey().isEmpty()) {
            for (String deleteKey : dto.getDeleteImageKey()) {
                AcmdImage imageToDelete = accommodation.getImages().stream()
                        .filter(img -> img.getImgKey().equals(deleteKey))
                        .findFirst()
                        .orElse(null);

                if (imageToDelete != null) {
                    accommodation.removeImage(imageToDelete);
                    s3Service.deleteFile(deleteKey);
                }
            }
        }


        return accommodationRepository.save(accommodation);
    }


    public void deleteAccommodation(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).
                orElseThrow(() -> new EntityNotFoundException("해당하는 숙소가 없습니다 Id : " + accommodationId));

        accommodationRepository.delete(accommodation);

    }
}
