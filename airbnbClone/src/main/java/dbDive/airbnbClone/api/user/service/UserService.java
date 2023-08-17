package dbDive.airbnbClone.api.user.service;

import dbDive.airbnbClone.api.user.dto.response.UserReviewResponse;
import dbDive.airbnbClone.api.user.dto.response.UserReviews;
import dbDive.airbnbClone.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserReviewResponse getUserReviews(Long userId, Pageable pageable) {
        PageImpl<UserReviews> allByUserId = userRepository.findAllByUserId(userId, pageable);

        return new UserReviewResponse(allByUserId);
    }
}
