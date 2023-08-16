package dbDive.airbnbClone.api.review.service;

import dbDive.airbnbClone.api.review.dto.response.ReviewResponse;
import dbDive.airbnbClone.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResponse getReviews(Long acmdId, Pageable pageable){
        return new ReviewResponse(reviewRepository.findAllByAcmdId(acmdId, pageable));
    }
}
