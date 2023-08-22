package dbDive.airbnbClone.common.dto;

import lombok.Getter;

@Getter
public class JwtFilterExceptionDto {
    private String message;

    public JwtFilterExceptionDto(String message) {
        this.message = message;
    }
}
