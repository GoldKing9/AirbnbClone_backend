package dbDive.airbnbClone.entity.resevation;

import dbDive.airbnbClone.entity.BaseTimeEntity;
import dbDive.airbnbClone.entity.accommodation.Accommodation;
import dbDive.airbnbClone.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE Reservation SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private boolean isDeleted = false;
    private int totalPrice;
    private int guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acmd_id")
    private Accommodation accommodation;

}
