package dbDive.airbnbClone.api.accommodation.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccommodationDataDto {
        private Long accommodationId;
        private String mainAddress;
        private int price;
        private double ratingAvg;
        private String detailAddress;
        private String acmdName;
        private String acmdDescription;
        private int guest;
        private int bedroom;
        private int bed;
        private int bathroom;
        private List<ImageDto> images;

    public AccommodationDataDto(Long accommodationId, String mainAddress, int price, double ratingAvg) {
        this.accommodationId = accommodationId;
        this.mainAddress = mainAddress;
        this.price = price;
        this.ratingAvg = ratingAvg;
    }
}
