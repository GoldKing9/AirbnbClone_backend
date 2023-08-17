package dbDive.airbnbClone.api.accommodation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AccommodationDataDto {
        private Long accommodationId;
        private String mainAddress;
        private List<ImageDto> images;
        private int price;
        private double ratingAvg;

    public AccommodationDataDto(Long accommodationId, String mainAddress, int price, double ratingAvg) {
        this.accommodationId = accommodationId;
        this.mainAddress = mainAddress;
        this.price = price;
        this.ratingAvg = ratingAvg;
    }
}
