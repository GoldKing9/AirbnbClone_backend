package dbDive.airbnbClone.api.user.dto.request;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;

}
