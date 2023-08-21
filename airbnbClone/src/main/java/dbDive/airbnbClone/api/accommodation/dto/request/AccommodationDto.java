package dbDive.airbnbClone.api.accommodation.dto.request;


import lombok.Getter;
import lombok.ToString;

@Getter
public class AccommodationDto {
    private String mainAddress;
    private String detailAddress;
    private int guest;
    private int bedroom;
    private int bed;
    private int bathroom;
    private String acmdName;
    private String acmdDescription;
    private int price;
}