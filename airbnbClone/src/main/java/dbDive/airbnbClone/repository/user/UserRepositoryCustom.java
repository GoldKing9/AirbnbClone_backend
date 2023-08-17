package dbDive.airbnbClone.repository.user;

import dbDive.airbnbClone.api.user.dto.response.UserReviews;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    PageImpl<UserReviews> findAllByUserId(Long userId, Pageable pageable);
}
