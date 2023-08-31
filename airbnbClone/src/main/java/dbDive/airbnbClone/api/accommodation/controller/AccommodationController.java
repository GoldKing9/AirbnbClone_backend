package dbDive.airbnbClone.api.accommodation.controller;

import dbDive.airbnbClone.api.accommodation.dto.request.AccommodationEditRequest;
import dbDive.airbnbClone.api.accommodation.dto.request.AccommodationReqeust;
import dbDive.airbnbClone.api.accommodation.dto.request.SearchRequest;
import dbDive.airbnbClone.api.accommodation.dto.response.DetailAcmdResponse;
import dbDive.airbnbClone.api.accommodation.dto.response.SearchResponse;
import dbDive.airbnbClone.api.accommodation.service.AccommodationService;
import dbDive.airbnbClone.config.auth.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/api/accommodation/search")
    public SearchResponse search(Pageable pageable,@ModelAttribute SearchRequest request) {
        return accommodationService.search(pageable, request);
    }

    @GetMapping("/api/accommodation/{accommodationId}")
    public DetailAcmdResponse detail(@PathVariable Long accommodationId) {
        return accommodationService.detail(accommodationId);
    }

    @PostMapping("/api/auth/accommodation")
    public void registerAccommodation(@RequestPart AccommodationReqeust request,
                                      @RequestPart List<MultipartFile> images,
                                      @AuthenticationPrincipal AuthUser authUser) {
        accommodationService.saveAccommodation(request, images, authUser.getUser());
    }

    @PutMapping("/api/auth/accommodation/{accommodationId}")
    public void editAccommodation(@PathVariable Long accommodationId,
                                  @RequestPart(value = "request") AccommodationEditRequest request,
                                  @RequestPart() List<MultipartFile> images,
                                  @AuthenticationPrincipal AuthUser authUser) {
        accommodationService.editAccommodation(accommodationId, request, images, authUser.getUser());
    }

    @DeleteMapping("/api/auth/accommodation/{accommodationId}")
    public void deleteAccommodation(@PathVariable Long accommodationId,
                                    @AuthenticationPrincipal AuthUser authUser) {
        accommodationService.deleteAccommodation(accommodationId, authUser.getUser());
    }
}
