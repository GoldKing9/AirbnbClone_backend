package dbDive.airbnbClone.api.review.dto.response;

import lombok.Getter;

@Getter
public class ReviewModifyResponse {
    private String comment;

    public ReviewModifyResponse(String comment) {
        this.comment = comment;
    }
}
