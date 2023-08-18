package dbDive.airbnbClone.api.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SelectReservationDto {
    private List<String> images;
    private Long accommodationId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long userId;
    private String username;
    private String userDescription;
    private int totalPrice;

    public SelectReservationDto(Long accommodationId,LocalDate checkIn, LocalDate checkOut, Long userId, String username, String userDescription, int totalPrice) {
        this.accommodationId = accommodationId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.userId = userId;
        this.username = username;
        this.userDescription = userDescription;
        this.totalPrice = totalPrice;
    }
}
