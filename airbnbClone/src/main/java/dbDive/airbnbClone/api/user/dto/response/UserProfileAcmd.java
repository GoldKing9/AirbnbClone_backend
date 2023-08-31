package dbDive.airbnbClone.api.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserProfileAcmd {
    private Long accommodationId;
    private String acmdImageUrl;
    private String acmdRatingAvg;
    private String mainAddress;

    public UserProfileAcmd(Long accommodationId, String acmdImageUrl, double acmdRatingAvg, String mainAddress) {
        this.accommodationId = accommodationId;
        this.acmdImageUrl = acmdImageUrl;
        this.acmdRatingAvg = String.format("%.2f", acmdRatingAvg);
        this.mainAddress = mainAddress;
    }
}
