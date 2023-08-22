package dbDive.airbnbClone.api.reservation.controller;

import dbDive.airbnbClone.api.reservation.dto.response.SelectReservationDto;
import dbDive.airbnbClone.api.reservation.dto.request.BookRequest;
import dbDive.airbnbClone.api.reservation.dto.response.HostTotalAccommodationResponse;
import dbDive.airbnbClone.api.reservation.dto.response.TotalAccommodationResponse;
import dbDive.airbnbClone.api.reservation.service.ReservationService;
import dbDive.airbnbClone.config.auth.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{accommodationId}/book")
    public void book(@PathVariable Long accommodationId, @RequestBody BookRequest request, @AuthenticationPrincipal AuthUser authUser) {
        reservationService.bookAccommodation(accommodationId, request,authUser);
    }

    @GetMapping("/user/reservations")
    public TotalAccommodationResponse getAllReservation(Pageable pageable, @AuthenticationPrincipal AuthUser authUser) {
        return reservationService.getAllReservation(pageable,authUser);
    }

    @GetMapping("/user/reservations/{reservationId}")
    public SelectReservationDto getReservation(@PathVariable Long reservationId, @AuthenticationPrincipal AuthUser authUser) {
        return reservationService.getReservation(reservationId,authUser);
    }


    @DeleteMapping("/accommodation/reservations/{reservationId}")
    public void deleteReservation(@PathVariable Long reservationId, @AuthenticationPrincipal AuthUser authUser) {
        reservationService.deleteReservation(reservationId, authUser);

    }

    @GetMapping("/host")
    public HostTotalAccommodationResponse getHostAllReservations(Pageable pageable, @AuthenticationPrincipal AuthUser authUser) {
        return reservationService.getHostAllReservations(pageable,authUser);
    }
}
