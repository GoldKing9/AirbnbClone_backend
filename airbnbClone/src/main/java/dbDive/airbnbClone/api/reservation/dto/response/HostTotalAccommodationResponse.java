package dbDive.airbnbClone.api.reservation.dto.response;

import lombok.Getter;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Getter
public class HostTotalAccommodationResponse {

    private List<HostReservationDto> reservations;
    private int totalPage;
    private int currentPage;

    public HostTotalAccommodationResponse(PageImpl<HostReservationDto> reservations) {
        this.reservations = reservations.getContent();
        this.totalPage = reservations.getTotalPages();
        this.currentPage = reservations.getNumber();
    }
}
