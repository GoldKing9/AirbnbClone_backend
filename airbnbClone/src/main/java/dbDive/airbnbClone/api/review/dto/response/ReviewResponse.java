package dbDive.airbnbClone.api.review.dto.response;

import lombok.Getter;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Getter
public class ReviewResponse {
    private List<ReviewComment> result;
    private int totalPages;
    private int currentPage;

    public ReviewResponse(PageImpl<ReviewComment> page){
        this.result = page.getContent();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber()+1;
    }
}
