package dbDive.airbnbClone.api.review.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReviewRequest {

    private String comment;
    @Size(min = 1, max = 5)
    private int rating;

    public ReviewRequest(String comment) {
        this.comment = comment;
    }

    public ReviewRequest(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }
}
