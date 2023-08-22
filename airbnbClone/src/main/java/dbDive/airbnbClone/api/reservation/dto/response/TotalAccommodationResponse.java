package dbDive.airbnbClone.api.reservation.dto.response;

import lombok.Getter;
import org.springframework.data.domain.PageImpl;

import java.util.List;
@Getter
public class TotalAccommodationResponse {

    private List<ReservationDto> reservations;
    private int totalPage;
    private int currentPage;

    public TotalAccommodationResponse(PageImpl<ReservationDto> reservations) {
        this.reservations = reservations.getContent();
        this.totalPage = reservations.getTotalPages();
        this.currentPage = reservations.getNumber();
    }
}

