package dbDive.airbnbClone.api.review.controller;

import dbDive.airbnbClone.api.review.dto.request.ReviewRequest;
import dbDive.airbnbClone.api.review.dto.response.ReviewModifyResponse;
import dbDive.airbnbClone.api.review.dto.response.ReviewResponse;
import dbDive.airbnbClone.api.review.service.ReviewService;
import dbDive.airbnbClone.config.auth.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews/{accommodationId}")
    public ReviewResponse reviews(
            @PathVariable Long accommodationId,
            Pageable pageable
    ) {
        return reviewService.getReviews(accommodationId, pageable);
    }

    @PostMapping("/auth/user/accommodation/review/{accommodationId}")
    public void postReview(@PathVariable Long accommodationId,
                           @AuthenticationPrincipal AuthUser authUser,
                           @Valid @RequestBody ReviewRequest request) {
        reviewService.post(accommodationId, authUser.getUser(), request);
    }

    @PutMapping("/auth/user/accommodation/review/{reviewId}")
    public ReviewModifyResponse modify(@PathVariable Long reviewId,
                                       @AuthenticationPrincipal AuthUser authUser,
                                       @RequestBody ReviewRequest request) {
        return reviewService.modify(reviewId, authUser.getUser().getId(), request);
    }

    @DeleteMapping("/auth/user/accommodation/review/{reviewId}")
    public void delete(@PathVariable Long reviewId,
                       @AuthenticationPrincipal AuthUser authUser) {
        reviewService.delete(reviewId, authUser.getUser().getId());
    }
}
