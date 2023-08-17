package dbDive.airbnbClone.entity.accommodation;

import dbDive.airbnbClone.entity.BaseTimeEntity;
import dbDive.airbnbClone.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE Accommodation SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Accommodation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mainAddress;
    private String detailAddress;
    private int bed;
    private int bedroom;
    private int bathroom;
    private int guest;
    private String acmdName;
    private String acmdDescription;
    private int price;
    private boolean isDeleted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Accommodation(String mainAddress, String detailAddress, int bed, int bedroom, int bathroom, int guest, String acmdName, String acmdDescription, int price, boolean isDeleted, User user) {
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.bed = bed;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.guest = guest;
        this.acmdName = acmdName;
        this.acmdDescription = acmdDescription;
        this.price = price;
        this.isDeleted = false;
        this.user = user;
    }
}
