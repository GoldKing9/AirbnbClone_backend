package dbDive.airbnbClone.repository.review;

import dbDive.airbnbClone.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
