package dbDive.airbnbClone.repository.reservation;

import dbDive.airbnbClone.entity.resevation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long>,ReservationRepositoryCustom {
}
