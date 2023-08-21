package dbDive.airbnbClone.api.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserProfileAcmd {
    private Long accmmodationId;
    private String acmdImageUrl;
    private String acmdRatingAvg;
    private String mainAddress;

    public UserProfileAcmd(Long accmmodationId, String acmdImageUrl, double acmdRatingAvg, String mainAddress) {
        this.accmmodationId = accmmodationId;
        this.acmdImageUrl = acmdImageUrl;
        this.acmdRatingAvg = String.format("%.2f", acmdRatingAvg);
        this.mainAddress = mainAddress;
    }
}
