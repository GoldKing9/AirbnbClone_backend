package dbDive.airbnbClone.api.review.controller;

import dbDive.airbnbClone.api.review.dto.request.ReviewRequest;
import dbDive.airbnbClone.api.review.dto.response.ReviewResponse;
import dbDive.airbnbClone.api.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/api/reviews/{accommodationId}")
    public ReviewResponse reviews(
            @PathVariable Long accommodationId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page-1, size);
        return reviewService.getReviews(accommodationId, pageable);
    }

    @PostMapping("/api/auth/user/accommodation/{accommodationId}/review/{userId}")
    public void postReview(@PathVariable Long accommodationId, @PathVariable Long userId, @RequestBody ReviewRequest request){
        // TODO : LOGIN기능 개발 후 수정
        reviewService.post(accommodationId, userId, request);
    }
}
