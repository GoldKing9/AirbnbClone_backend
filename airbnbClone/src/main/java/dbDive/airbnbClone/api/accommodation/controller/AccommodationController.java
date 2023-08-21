package dbDive.airbnbClone.api.accommodation.controller;

import dbDive.airbnbClone.api.accommodation.dto.request.AccommodationDto;
import dbDive.airbnbClone.api.accommodation.dto.request.AccommodationEditDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    @PostMapping("/api/accommodation")
    public ResponseEntity<Accommodation> registerAccommodation(@RequestPart AccommodationDto dto,
                                                               @RequestPart List<MultipartFile> images) {

        Accommodation savedAccommodation = accommodationService.saveAccommodation(dto, images);

        return new ResponseEntity<>(savedAccommodation, HttpStatus.CREATED);
    }
    @PutMapping("/api/accommodation/{accommodationId}")
    public ResponseEntity<Accommodation> editAccommodation(@PathVariable Long accommodationId,
                                                           @RequestPart(value = "dto") AccommodationEditDto dto,
                                                           @RequestPart(value = "newImages", required = false) List<MultipartFile> newImages)
    {
        Accommodation updatedAccommodation = accommodationService.editAccommodation(accommodationId, dto, newImages);

        if (updatedAccommodation != null)
            return new ResponseEntity<>(updatedAccommodation, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/api/accommodation/{accommodationId}")
    public ResponseEntity<Accommodation> deleteAccommodation(@PathVariable Long accommodationId) {

        try{
            accommodationService.deleteAccommodation(accommodationId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
