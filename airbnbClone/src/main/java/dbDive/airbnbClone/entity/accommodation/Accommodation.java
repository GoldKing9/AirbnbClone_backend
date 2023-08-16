package dbDive.airbnbClone.entity.accommodation;

import dbDive.airbnbClone.entity.BaseTimeEntity;
import dbDive.airbnbClone.entity.user.User;
import jakarta.persistence.*;
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
    private boolean isDelete;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

}
