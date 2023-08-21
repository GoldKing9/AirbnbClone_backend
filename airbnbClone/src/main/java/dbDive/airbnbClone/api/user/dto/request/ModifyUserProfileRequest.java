package dbDive.airbnbClone.api.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyUserProfileRequest {
    private String userDescription;

    public ModifyUserProfileRequest(String userDescription) {
        this.userDescription = userDescription;
    }
}
