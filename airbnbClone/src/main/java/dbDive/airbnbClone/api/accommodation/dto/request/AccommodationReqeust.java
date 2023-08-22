package dbDive.airbnbClone.api.accommodation.dto.request;


import lombok.Getter;

@Getter
public class AccommodationReqeust {
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
