package hello.loginservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    ROLE_USER("ROLE_USER"), //회원가입 한 사람
    ROLE_ADMIN("ROLE_ADMIN"), //개발자
    ROLE_GUEST("ROLE_GUEST"); //일반 사용자

    private final String value;
}