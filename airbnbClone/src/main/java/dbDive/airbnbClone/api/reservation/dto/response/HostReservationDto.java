package dbDive.airbnbClone.api.reservation.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HostReservationDto {
    private Long reservationId;
    private Long userId;
    private String username;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long accommodationId;
    private String acmdName;
    private String images;
    private String status;
    private int totalPrice;

    public HostReservationDto(Long reservationId,Long userId, String username, LocalDate checkIn, LocalDate checkOut, Long accommodationId, String acmdName, String images, String status, int totalPrice) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.username = username;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.accommodationId = accommodationId;
        this.acmdName = acmdName;
        this.images = images;
        this.status = status;
        this.totalPrice = totalPrice;
    }
}
