package dbDive.airbnbClone.api.reservation.controller;

import dbDive.airbnbClone.api.reservation.dto.SelectReservationDto;
import dbDive.airbnbClone.api.reservation.dto.request.BookRequest;
import dbDive.airbnbClone.api.reservation.dto.response.HostTotalAccommodationResponse;
import dbDive.airbnbClone.api.reservation.dto.response.TotalAccommodationResponse;
import dbDive.airbnbClone.api.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class ReservationController {
    /**
     * TODO
     *
     * @AuthenticationPrincipal 넣어주기
     */
    private final ReservationService reservationService;

    @PostMapping(value = "/{accommodationId}/book") //숙소 예약 -> 프론트에서  checkIn/checkOut/totalPrice/guest 받아서 예약 진행
    public void book(@PathVariable Long accommodationId, @RequestBody BookRequest request) {
        reservationService.bookAccommodation(accommodationId, request);
    }

    @GetMapping(value = "/user/reservations") // 게스트 - 숙소 예약 전체 조회
    public TotalAccommodationResponse allAccommodations(Pageable pageable) {
        return reservationService.allAccommodations(pageable);
    }

    @GetMapping("/user/reservations/{reservationId}") // 게스트 - 숙소 예약 단건 조회
    public SelectReservationDto selectAccommodation(@PathVariable Long reservationId) {
        return reservationService.selectAccommodations(reservationId);
    }

    @DeleteMapping("/accommodation/reservation/{reservationId}")
    public void deleteAccommodation(@PathVariable Long reservationId) {
        reservationService.deleteAccommodation(reservationId);
    }


    @GetMapping("/host/{userId}")
    public HostTotalAccommodationResponse hostAllAccommodations(@PathVariable Long userId, Pageable pageable) {
        return reservationService.hostAllAccommodations(userId, pageable);
    }
}
