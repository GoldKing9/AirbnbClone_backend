package dbDive.airbnbClone.api.accommodation.controller;

import dbDive.airbnbClone.api.accommodation.dto.AccommodationDataDto;
import dbDive.airbnbClone.api.accommodation.dto.request.SearchRequest;
import dbDive.airbnbClone.api.accommodation.dto.response.DetailAcmdResponse;
import dbDive.airbnbClone.api.accommodation.dto.response.SearchResponse;
import dbDive.airbnbClone.api.accommodation.service.AccommodationService;
import dbDive.airbnbClone.entity.accommodation.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/accommodation")
    public ResponseEntity<Accommodation> registerAccommodation(@RequestBody AccommodationDataDto dto) {
        Accommodation savedAccommodation = accommodationService.saveAccommodation(dto);
        return new ResponseEntity<>(savedAccommodation, HttpStatus.CREATED);
    }

}
