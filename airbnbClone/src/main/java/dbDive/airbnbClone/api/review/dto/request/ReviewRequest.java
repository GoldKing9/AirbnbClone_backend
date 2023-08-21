package dbDive.airbnbClone.api.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReviewRequest {

    @Size(max = 255, message = "255자를 초과할 수 없습니다.")
    private String comment;
    @Min(value = 1, message = "1미만은 안됩니다.") @Max(value = 5, message = "5를 초과할 수 없습니다.")
    private int rating;

    public ReviewRequest(String comment) {
        this.comment = comment;
    }

    public ReviewRequest(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }
}
