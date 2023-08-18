package dbDive.airbnbClone.api.accommodation.service;

import dbDive.airbnbClone.api.accommodation.dto.AccommodationDataDto;
import dbDive.airbnbClone.api.accommodation.dto.ImageDto;
import dbDive.airbnbClone.api.accommodation.dto.request.SearchRequest;
import dbDive.airbnbClone.api.accommodation.dto.response.DetailAcmdResponse;
import dbDive.airbnbClone.api.accommodation.dto.response.SearchResponse;
import dbDive.airbnbClone.entity.accommodation.Accommodation;
import dbDive.airbnbClone.entity.accommodation.AcmdImage;
import dbDive.airbnbClone.repository.accommodation.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;

    public SearchResponse search(Pageable pageable, SearchRequest request) {
        PageImpl<AccommodationDataDto> accommodations = accommodationRepository.search(pageable,request);
        return new SearchResponse(accommodations);
    }

    public DetailAcmdResponse detail(Long accommodationId) {
        return accommodationRepository.findAccommodation(accommodationId);
    }

    public Accommodation saveAccommodation(AccommodationDataDto dto) {
        // 빌더패턴, 정적 팩토리 메서드
        Accommodation accommodation = new Accommodation(dto.getMainAddress(),  dto.getPrice(), dto.getDetailAddress(), dto.getAcmdName(), dto.getAcmdDescription(), dto.getGuest(), dto.getBedroom(), dto.getBed(), dto.getBathroom());

        for (ImageDto imageDto : dto.getImages()) {
            AcmdImage image = new AcmdImage(imageDto.acmdImageUrl());
            image.setAccommodation(accommodation);
            accommodation.getImages().add(image);
        }
        return accommodationRepository.save(accommodation);
    }
}
