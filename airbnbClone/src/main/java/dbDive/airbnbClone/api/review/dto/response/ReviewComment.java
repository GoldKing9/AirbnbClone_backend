package dbDive.airbnbClone.api.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ReviewComment {
    private Long userId;
    private String username;
    private String createdAt;
    private String comment;

    @Builder
    public ReviewComment(Long userId, String username, String createdAt, String comment) {
        this.userId = userId;
        this.username = username;
        this.createdAt = createdAt;
        this.comment = comment;
    }
}
