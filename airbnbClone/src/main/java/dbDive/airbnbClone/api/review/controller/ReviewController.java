package dbDive.airbnbClone.api.review.controller;

import dbDive.airbnbClone.api.review.dto.request.ReviewRequest;
import dbDive.airbnbClone.api.review.dto.response.ReviewModifyResponse;
import dbDive.airbnbClone.api.review.dto.response.ReviewResponse;
import dbDive.airbnbClone.api.review.service.ReviewService;
import dbDive.airbnbClone.config.auth.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/api/auth/user/accommodation/review/{accommodationId}")
    public void postReview(@PathVariable Long accommodationId,
                           @AuthenticationPrincipal AuthUser authUser,
                           @RequestBody ReviewRequest request){
        reviewService.post(accommodationId, authUser.getUser().getId(), request);
    }

    @PutMapping("/api/auth/user/accommodation/review/{reviewId}")
    public ReviewModifyResponse modify(@PathVariable Long reviewId,
                                       @AuthenticationPrincipal AuthUser authUser,
                                       @RequestBody ReviewRequest request){
        return reviewService.modify(reviewId, authUser.getUser().getId(), request);
    }

    @DeleteMapping("/api/auth/user/accommodation/review/{reviewId}")
    public void delete(@PathVariable Long reviewId,
                       @AuthenticationPrincipal AuthUser authUser){
        reviewService.delete(reviewId, authUser.getUser().getId());
    }
}
