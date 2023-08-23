package dbDive.airbnbClone.api.reservation.service;

import dbDive.airbnbClone.api.reservation.dto.request.BookRequest;
import dbDive.airbnbClone.api.reservation.dto.response.*;
import dbDive.airbnbClone.common.GlobalException;
import dbDive.airbnbClone.config.auth.AuthUser;
import dbDive.airbnbClone.entity.accommodation.Accommodation;
import dbDive.airbnbClone.entity.resevation.Reservation;
import dbDive.airbnbClone.repository.accommodation.AccommodationRepository;
import dbDive.airbnbClone.repository.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final AccommodationRepository accommodationRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public void bookAccommodation(Long accommodationId, BookRequest request, AuthUser authUser) {

        Accommodation findAcmd = accommodationRepository.findById(accommodationId).orElseThrow(() -> new GlobalException("존재하지 않는 숙소입니다,"));
        int guest = request.getGuest();
        int accommodationGuest = findAcmd.getGuest();

        if (guest > accommodationGuest) {
            throw new GlobalException("숙소 인원을 초과했습니다.");
        } else {
            Reservation reservation = Reservation.builder()
                    .checkIn(request.getCheckIn())
                    .checkOut(request.getCheckOut())
                    .totalPrice(request.getTotalPrice())
                    .guest(request.getGuest())
                    .user(authUser.getUser())
                    .accommodation(findAcmd)
                    .build();
            reservationRepository.save(reservation);
        }
    }

    public TotalAccommodationResponse getAllReservation(Pageable pageable, AuthUser authUser) {

        PageImpl<ReservationDto> result = reservationRepository.findAllReservations(pageable, authUser);
        return new TotalAccommodationResponse(result);

    }

    public SelectReservationDto getReservation(Long reservationId, AuthUser authUser) {
        SelectReservationDto selectReservation = reservationRepository.findSelectReservation(reservationId, authUser);
        return selectReservation;

    }

    @Transactional
    public void deleteReservation(Long reservationId, AuthUser authUser) {
        Reservation findReservation = reservationRepository.findById(reservationId).orElseThrow();
        Long reservationUserId = findReservation.getUser().getId();
        Long authUserId = authUser.getUser().getId();

        if (reservationUserId.equals(authUserId)) {
            reservationRepository.delete(findReservation);
        } else {
            throw new GlobalException("예약 취소 권한이 없습니다.");
        }
    }

    public HostTotalAccommodationResponse getHostAllReservations(Pageable pageable, AuthUser authUser) {
        PageImpl<HostReservationDto> hostReservation = reservationRepository.findHostReservation(pageable, authUser);
        return new HostTotalAccommodationResponse(hostReservation);
    }

}
