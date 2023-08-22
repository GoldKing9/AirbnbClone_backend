package dbDive.airbnbClone.api.accommodation.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class AccommodationEditRequest {
    private int guest;
    private int bedroom;
    private int bed;
    private int bathroom;
    private List<String> deleteImageKey;
    private String acmdName;
    private String acmdDescription;
    private int price;

}
