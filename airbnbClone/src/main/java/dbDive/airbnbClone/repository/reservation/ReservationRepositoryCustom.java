package dbDive.airbnbClone.repository.reservation;

import dbDive.airbnbClone.api.reservation.dto.response.HostReservationDto;
import dbDive.airbnbClone.api.reservation.dto.response.ReservationDto;
import dbDive.airbnbClone.api.reservation.dto.response.SelectReservationDto;
import dbDive.airbnbClone.config.auth.AuthUser;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ReservationRepositoryCustom {
    PageImpl<ReservationDto> findAllReservations(Pageable pageable, AuthUser authUser);

    SelectReservationDto findSelectReservation(Long reservationId, AuthUser authUser);

    PageImpl<HostReservationDto> findHostReservation(Pageable pageable, AuthUser authUser);

}
