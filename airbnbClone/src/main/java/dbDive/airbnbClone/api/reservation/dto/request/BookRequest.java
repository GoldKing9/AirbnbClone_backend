package dbDive.airbnbClone.api.reservation.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookRequest {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int totalPrice;
    private int guest;

    @Builder
    public BookRequest(LocalDate checkIn, LocalDate checkOut, int totalPrice, int guest) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
        this.guest = guest;
    }
}
