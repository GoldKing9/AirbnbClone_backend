package dbDive.airbnbClone.api.user.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignupReq {
    private String username;
    private String email;
    private String password;
    private LocalDate birth;
}
