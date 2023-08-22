package dbDive.airbnbClone.repository.reservation;

import dbDive.airbnbClone.api.reservation.dto.HostReservationDto;
import dbDive.airbnbClone.api.reservation.dto.ReservationDto;
import dbDive.airbnbClone.api.reservation.dto.SelectReservationDto;
import dbDive.airbnbClone.config.auth.AuthUser;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationRepositoryCustom {
    PageImpl<ReservationDto> findAllReservations(Pageable pageable, AuthUser authUser);

    SelectReservationDto findSelectReservation(Long reservationId, AuthUser authUser);

    PageImpl<HostReservationDto> findHostReservation(Pageable pageable, AuthUser authUser);

}
