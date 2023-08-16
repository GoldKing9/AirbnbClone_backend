package dbDive.airbnbClone.entity.review;

import dbDive.airbnbClone.entity.BaseTimeEntity;
import dbDive.airbnbClone.entity.accommodation.Accommodation;
import dbDive.airbnbClone.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Review SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private boolean isDeleted;
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acmd_id")
    private Accommodation accommodation;

    @Builder
    public Review(int rating, boolean isDeleted, String comment, User user, Accommodation accommodation) {
        this.rating = rating;
        this.isDeleted = isDeleted;
        this.comment = comment;
        this.user = user;
        this.accommodation = accommodation;
    }
}
