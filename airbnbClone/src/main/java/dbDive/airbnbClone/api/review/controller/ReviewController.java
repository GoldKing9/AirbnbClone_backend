package dbDive.airbnbClone.api.review.controller;

import dbDive.airbnbClone.api.review.dto.response.ReviewResponse;
import dbDive.airbnbClone.api.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/api/reviews/{accommodationId}")
    public ReviewResponse reviews(@PathVariable Long accommodationId, int page, int size){
        Pageable pageable = PageRequest.of(page-1, size);
        return reviewService.getReviews(accommodationId, pageable);
    }
}
