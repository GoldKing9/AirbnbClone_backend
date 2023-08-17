package dbDive.airbnbClone.api.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserReviews {
    private Long accommodationId;
    private String acmdImageUrl;
    private String acmdName;
    private Long userId;
    private String comment;
    private String username;
    private String createdAt;

    public UserReviews(Long accommodationId, String acmdImageUrl, String acmdName, Long userId, String comment, String username, String createdAt) {
        this.accommodationId = accommodationId;
        this.acmdImageUrl = acmdImageUrl;
        this.acmdName = acmdName;
        this.userId = userId;
        this.comment = comment;
        this.username = username;
        this.createdAt = createdAt;
    }
}
