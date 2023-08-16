package dbDive.airbnbClone.api.accommodation.controller;

import dbDive.airbnbClone.api.accommodation.dto.request.SearchRequest;
import dbDive.airbnbClone.api.accommodation.dto.response.DetailAcmdResponse;
import dbDive.airbnbClone.api.accommodation.dto.response.SearchResponse;
import dbDive.airbnbClone.api.accommodation.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/api/accommodation/search")
    public SearchResponse search(Pageable pageable, @ModelAttribute SearchRequest request) {
        return accommodationService.search(pageable, request);
    }

    @GetMapping("/api/accommodation/{accommodationId}")
    public DetailAcmdResponse detail(@PathVariable Long accommodationId) {
        return accommodationService.detail(accommodationId);
    }

}
