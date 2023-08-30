package dbDive.airbnbClone.api.user.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class FindEmailRequest {
    private String username;
    private LocalDate birth;
}
