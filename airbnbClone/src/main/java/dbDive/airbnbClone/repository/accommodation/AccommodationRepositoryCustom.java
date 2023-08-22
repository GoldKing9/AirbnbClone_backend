package dbDive.airbnbClone.repository.accommodation;

import dbDive.airbnbClone.api.accommodation.dto.response.AccommodationDataDto;
import dbDive.airbnbClone.api.accommodation.dto.request.SearchRequest;
import dbDive.airbnbClone.api.accommodation.dto.response.DetailAcmdResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface AccommodationRepositoryCustom {
    PageImpl<AccommodationDataDto> search(Pageable pageable, SearchRequest request);

    DetailAcmdResponse findAccommodation(Long accommodationId);
}
