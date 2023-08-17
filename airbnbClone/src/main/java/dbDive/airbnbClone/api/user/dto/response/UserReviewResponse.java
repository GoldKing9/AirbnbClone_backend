package dbDive.airbnbClone.api.user.dto.response;

import lombok.Getter;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Getter
public class UserReviewResponse {
    private long reviewCnt;
    private List<UserReviews> review;
    private int totalPages;
    private int currentPage;

    public UserReviewResponse(PageImpl<UserReviews> page) {
        this.reviewCnt = page.getTotalElements();
        this.review = page.getContent();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
    }
}
