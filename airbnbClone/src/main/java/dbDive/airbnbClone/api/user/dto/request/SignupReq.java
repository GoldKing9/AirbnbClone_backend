package dbDive.airbnbClone.api.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignupReq {
    @Pattern(regexp = "^[가-힣]{2,5}$")
    private String username;
    @Email
    private String email;
    @Pattern(regexp = "^[a-z0-9]{4,14}$")
    private String password;
    private LocalDate birth;
}
