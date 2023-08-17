package dbDive.airbnbClone.api.reservation.dto.response;

import lombok.Getter;

@Getter
public class SelectAccommodationResponse {
    private String imageUrl;
    private int checkInDate;
    private int checkOutDate;
    private Long userId;
    private String username;
    private String userDescription;
    private int totalPrice;

    public SelectAccommodationResponse(int checkInDate, int checkOutDate, Long userId, String username, String userDescription, int totalPrice) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.userId = userId;
        this.username = username;
        this.userDescription = userDescription;
        this.totalPrice = totalPrice;
    }
}
