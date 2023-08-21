package dbDive.airbnbClone.config.filter;

import lombok.Getter;

@Getter
public class JwtFilterExceptionDto {
    private String message;

    public JwtFilterExceptionDto(String message) {
        this.message = message;
    }
}
