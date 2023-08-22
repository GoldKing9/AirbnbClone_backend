package dbDive.airbnbClone.api.accommodation.service;

import com.amazonaws.services.s3.AmazonS3;
import dbDive.airbnbClone.api.accommodation.dto.response.AccommodationDataDto;
import dbDive.airbnbClone.api.accommodation.dto.request.AccommodationReqeust;
import dbDive.airbnbClone.api.accommodation.dto.request.AccommodationEditRequest;
import dbDive.airbnbClone.api.accommodation.dto.request.SearchRequest;
import dbDive.airbnbClone.api.accommodation.dto.response.DetailAcmdResponse;
import dbDive.airbnbClone.api.accommodation.dto.response.SearchResponse;
import dbDive.airbnbClone.common.GlobalException;
import dbDive.airbnbClone.common.S3Service;
import dbDive.airbnbClone.entity.accommodation.Accommodation;
import dbDive.airbnbClone.entity.accommodation.AcmdImage;
import dbDive.airbnbClone.entity.user.User;
import dbDive.airbnbClone.repository.accommodation.AccommodationRepository;
import dbDive.airbnbClone.repository.accommodation.AcmdImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final AcmdImageRepository acmdImageRepository;
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

    @Transactional
    public void saveAccommodation(AccommodationReqeust dto,
                                  List<MultipartFile> images,
                                  User user) {

        Accommodation accommodation = Accommodation.builder()
                .mainAddress(dto.getMainAddress())
                .price(dto.getPrice())
                .detailAddress(dto.getDetailAddress())
                .acmdName(dto.getAcmdName())
                .acmdDescription(dto.getAcmdDescription())
                .guest(dto.getGuest())
                .bed(dto.getBed())
                .bedroom(dto.getBedroom())
                .bathroom(dto.getBathroom())
                .user(user)
                .build();

        for (MultipartFile imageFile : images) {
            String imgKey = s3Service.uploadFile(imageFile);
            String imageUrl = amazonS3.getUrl(bucketName, imgKey).toExternalForm();
            AcmdImage acmdImage = new AcmdImage(imageUrl, imgKey);
            accommodation.addImage(acmdImage);
        }

        accommodationRepository.save(accommodation);
    }

    @Transactional
    public void editAccommodation(Long accommodationId,
                                           AccommodationEditRequest dto,
                                           List<MultipartFile> newImages,
                                           User user) {

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 숙소가 없습니다 Id : " + accommodationId));

        if (!accommodation.getUser().getId().equals(user.getId())) {
            throw new GlobalException("이 숙소를 편집할 권한이 없습니다.");
        }

        accommodation.updateAccommodationDetails(dto.getBed(), dto.getBedroom(),
                                                    dto.getBathroom(), dto.getGuest(),
                                                    dto.getAcmdName(), dto.getAcmdDescription(),
                                                    dto.getPrice(), user);

        List<AcmdImage> uploadImage = new ArrayList<>();
        if (newImages != null && !newImages.isEmpty()) {
            for (MultipartFile newImageFile : newImages) {
                String newImgKey = s3Service.uploadFile(newImageFile);
                String newImageUrl = amazonS3.getUrl(bucketName, newImgKey).toExternalForm();
                AcmdImage newAcmdImage = new AcmdImage(newImageUrl, newImgKey);
                uploadImage.add(newAcmdImage);
            }
        }
        for (AcmdImage oldImage : accommodation.getImages()) {
            s3Service.deleteFile(oldImage.getImgKey());
        }
        acmdImageRepository.deleteAllInBatch(accommodation.getImages());

        for (AcmdImage newImage : uploadImage) {
            accommodation.addImage(newImage);
        }

        accommodationRepository.save(accommodation);
    }

    @Transactional
    public void deleteAccommodation(Long accommodationId,
                                       User user) {

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 숙소가 없습니다 Id : " + accommodationId));


        if (!accommodation.getUser().getId().equals(user.getId())) {
            throw new GlobalException("이 숙소를 삭제할 권한이 없습니다.");
        }

        for (AcmdImage image : accommodation.getImages()) {
            s3Service.deleteFile(image.getImgKey());
        }

        accommodationRepository.delete(accommodation);
    }
}
