package dbDive.airbnbClone.repository.accommodation;

import dbDive.airbnbClone.entity.accommodation.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
