package dbDive.airbnbClone.api.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginReq {
    @Email
    private String email;
    @Pattern(regexp = "^[a-z0-9]{4,14}$")
    private String password;
}
