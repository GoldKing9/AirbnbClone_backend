package dbDive.airbnbClone.repository.reservation;

import dbDive.airbnbClone.api.reservation.dto.HostReservationDto;
import dbDive.airbnbClone.api.reservation.dto.ReservationDto;
import dbDive.airbnbClone.api.reservation.dto.SelectReservationDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationRepositoryCustom {
    PageImpl<ReservationDto> findAllReservations(Pageable pageable);

    SelectReservationDto findSelectReservation(Long reservationId);

    PageImpl<HostReservationDto> findHostReservation(Long userId, Pageable pageable);

}
