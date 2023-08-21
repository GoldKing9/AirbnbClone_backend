package dbDive.airbnbClone.entity.resevation;

import dbDive.airbnbClone.entity.BaseTimeEntity;
import dbDive.airbnbClone.entity.accommodation.Accommodation;
import dbDive.airbnbClone.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Reservation SET is_deleted = true WHERE id = ?")
//@Table(indexes = {
//        @Index(name = "i_checkIn_checkOut", columnList = "checkIn, checkOut")
//})
//@Where(clause = "is_deleted = false")
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private boolean isDeleted;
    private int totalPrice;
    private int guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acmd_id")
    private Accommodation accommodation;

    @Builder
    public Reservation(LocalDate checkIn, LocalDate checkOut, boolean isDeleted, int totalPrice, int guest, User user, Accommodation accommodation) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.isDeleted = isDeleted;
        this.totalPrice = totalPrice;
        this.guest = guest;
        this.user = user;
        this.accommodation = accommodation;
    }
}
