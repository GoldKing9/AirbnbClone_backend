package dbDive.airbnbClone.api.reservation.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReservationDto {
    private Long accommodationId;
    private Long userId;
    private String city;
    private String username;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<String> images;

    public ReservationDto(Long accommodationId,Long userId, String city, String username, LocalDate checkIn, LocalDate checkOut) {
        this.accommodationId = accommodationId;
        this.userId = userId;
        this.city = city;
        this.username = username;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
