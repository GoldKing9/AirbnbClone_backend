package dbDive.airbnbClone.api.reservation.service;

import dbDive.airbnbClone.api.reservation.dto.HostReservationDto;
import dbDive.airbnbClone.api.reservation.dto.ReservationDto;
import dbDive.airbnbClone.api.reservation.dto.SelectReservationDto;
import dbDive.airbnbClone.api.reservation.dto.request.BookRequest;
import dbDive.airbnbClone.api.reservation.dto.response.HostTotalAccommodationResponse;
import dbDive.airbnbClone.api.reservation.dto.response.TotalAccommodationResponse;
import dbDive.airbnbClone.common.GlobalException;
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
public class ReservationService {

    private final AccommodationRepository accommodationRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public void bookAccommodation(Long accommodationId, BookRequest request) {

        //숙소 예약 -> 프론트에서  checkIn/checkOut/totalPrice/guest 받아서 예약 진행
        Accommodation findAcmd = accommodationRepository.findById(accommodationId).orElseThrow(() -> new GlobalException("존재하지 않는 숙소입니다,"));
        Reservation reservation = Reservation.builder()
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .totalPrice(request.getTotalPrice())
                .guest(request.getGuest())
                .accommodation(findAcmd)
                .build();
        reservationRepository.save(reservation);
    }

    public TotalAccommodationResponse allAccommodations(Pageable pageable) {

        PageImpl<ReservationDto> result = reservationRepository.findAllReservations(pageable);
        return new TotalAccommodationResponse(result);

    }

    public SelectReservationDto selectAccommodations(Long reservationId) {
        // 게스트 - 숙소 예약 단건 조회
        SelectReservationDto selectReservation = reservationRepository.findSelectReservation(reservationId);
        return selectReservation;

    }

    public void deleteAccommodation(Long reservationId) {
        // 게스트 - 예약 취소
        Reservation findReservation = reservationRepository.findById(reservationId).orElseThrow();
        reservationRepository.delete(findReservation);
    }

    public HostTotalAccommodationResponse hostAllAccommodations(Long userId, Pageable pageable) {
        // 호스트 - 예약 조회
        /**
         * 1. 등록된 accommodation에서 userId로 accommodationId 찾기
         * 2. accommodationId 으로 같은 accommodationId 을 가지고 있는 acmd_image, reservation 찾기
         * 3. 상태 체크
         * - checkOut > LocalDate.now() && isDeleted == false, 예약중
         * - checkOut < LocalDate.now() && isDeleted == false, 이용완료
         * - isDeleted == true, 취소
         */

        PageImpl<HostReservationDto> hostReservation = reservationRepository.findHostReservation(userId, pageable);
        return new HostTotalAccommodationResponse(hostReservation);
    }

}
