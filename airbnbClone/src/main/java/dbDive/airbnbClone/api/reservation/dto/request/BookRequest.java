package dbDive.airbnbClone.api.reservation.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookRequest {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int totalPrice;
    private int guest;

}
