package dbDive.airbnbClone.entity.user;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("사용자"), ADMIN("관리자");
    private String value;
    UserRole(String value) {
        this.value = value;
    }
}
