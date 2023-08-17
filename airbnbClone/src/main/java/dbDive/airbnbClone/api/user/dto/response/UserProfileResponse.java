package dbDive.airbnbClone.api.user.dto.response;

import dbDive.airbnbClone.api.review.dto.response.ReviewComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileResponse {
    private String username;
    private Long commentCnt;
    private String hostRating;
    private String userDescription;
    private List<ReviewComment> reviews;
    private List<UserProfileAcmd> accommodations;

    public UserProfileResponse(String username, Long commentCnt, double hostRating, String userDescription) {
        this.username = username;
        this.commentCnt = commentCnt;
        this.hostRating = String.format("%.2f", hostRating);
        this.userDescription = userDescription;
    }
}
