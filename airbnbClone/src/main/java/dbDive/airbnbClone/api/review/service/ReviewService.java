package dbDive.airbnbClone.api.review.service;

import dbDive.airbnbClone.api.review.dto.request.ReviewRequest;
import dbDive.airbnbClone.api.review.dto.response.ReviewModifyResponse;
import dbDive.airbnbClone.api.review.dto.response.ReviewResponse;
import dbDive.airbnbClone.common.GlobalException;
import dbDive.airbnbClone.entity.accommodation.Accommodation;
import dbDive.airbnbClone.entity.review.Review;
import dbDive.airbnbClone.entity.user.User;
import dbDive.airbnbClone.repository.accommodation.AccommodationRepository;
import dbDive.airbnbClone.repository.review.ReviewRepository;
import dbDive.airbnbClone.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    public ReviewResponse getReviews(Long acmdId, Pageable pageable){
        return new ReviewResponse(reviewRepository.findAllByAcmdId(acmdId, pageable));
    }

    @Transactional
    public void post(Long accommodationId, Long userId, ReviewRequest request) {
        Accommodation acmd = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new GlobalException("존재하지 않는 게시물입니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException("존재하지 않는 회원입니다."));

        Review review = Review.builder()
                .accommodation(acmd)
                .user(user)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        reviewRepository.save(review);
    }
    @Transactional
    public ReviewModifyResponse modify(Long reviewId, Long userId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new GlobalException("존재하지 않는 리뷰입니다."));
        if(!review.getUser().getId().equals(userId)){
            throw new GlobalException("수정 권한이 없습니다.");
        }

        review.update(request.getComment());

        return new ReviewModifyResponse(request.getComment());
    }

    public void delete(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new GlobalException("존재하지 않는 리뷰입니다."));
        if(!review.getUser().getId().equals(userId)){
            throw new GlobalException("잘못된 접근입니다.");
        }
        reviewRepository.deleteById(reviewId);
    }
}
