package dbDive.airbnbClone.api.accommodation.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailAcmdResponse {
    private String mainAddress;
    private String detailAddress;
    private int price;
    private Long userId;
    private String username;
    private String userDescription;
    private int guest;
    private int bed;
    private int bedroom;
    private int bathroom;
    private double ratingAvg;
    private Long reviewCnt;
    private List<ImageDto> images;

    public DetailAcmdResponse(String mainAddress, String detailAddress, int price, Long userId, String username, String userDescription, int guest, int bed, int bedroom, int bathroom, double ratingAvg, Long reviewCnt) {
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.price = price;
        this.userId = userId;
        this.username = username;
        this.userDescription = userDescription;
        this.guest = guest;
        this.bed = bed;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.ratingAvg = ratingAvg;
        this.reviewCnt = reviewCnt;
    }
}
