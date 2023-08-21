package dbDive.airbnbClone.api.accommodation.service;

import dbDive.airbnbClone.api.accommodation.dto.AccommodationDataDto;
import dbDive.airbnbClone.api.accommodation.dto.request.SearchRequest;
import dbDive.airbnbClone.api.accommodation.dto.response.DetailAcmdResponse;
import dbDive.airbnbClone.api.accommodation.dto.response.SearchResponse;
import dbDive.airbnbClone.repository.accommodation.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;

    @Cacheable(cacheNames = "accommodations", key = "{#pageable.pageNumber, #request}")
    public SearchResponse search(Pageable pageable, SearchRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        PageImpl<AccommodationDataDto> accommodations = accommodationRepository.search(pageable,request);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
        return new SearchResponse(accommodations);
    }

    public DetailAcmdResponse detail(Long accommodationId) {
        return accommodationRepository.findAccommodation(accommodationId);
    }
}
