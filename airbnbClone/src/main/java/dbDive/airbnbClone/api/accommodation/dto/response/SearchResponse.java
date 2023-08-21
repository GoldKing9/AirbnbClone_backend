package dbDive.airbnbClone.api.accommodation.dto.response;

import dbDive.airbnbClone.api.accommodation.dto.AccommodationDataDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchResponse {
    private List<AccommodationDataDto> results;
    private int totalPages;
    private int currentPage;

    public SearchResponse(PageImpl<AccommodationDataDto> accommodations) {
        this.results = accommodations.getContent();
        this.totalPages = accommodations.getTotalPages();
        this.currentPage = accommodations.getNumber();
    }
}
