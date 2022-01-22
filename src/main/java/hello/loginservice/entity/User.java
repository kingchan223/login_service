package hello.loginservice.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@NoArgsConstructor(access= AccessLevel.PROTECTED)/*프록시 객체를 위해 protect로 기본 생성자*/
@Getter
@Entity
public class User extends DateBaseEntity implements Serializable {
    @Id @GeneratedValue
    private Long id;

    private String email;
    private String password;

    private String username;
    private String sex;//F, M
    private String phone;//'-'없이
    private String birthday;//생년월일 7자리
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String refreshToken;

    private String provider;
    private String providerId;

    public static User makeUser(String email, String password) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.username = "이찬영";
        user.sex = "F";
        user.phone = "01088881111";
        user.birthday = "970223";
        user.nickname = "kokiyo97";
        user.role = UserRole.ROLE_USER;
        return user;
    }

    public static User makeUser(String email, String password, String refreshToken) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.username = "이찬영";
        user.sex = "F";
        user.phone = "01088881111";
        user.birthday = "970223";
        user.nickname = "kokiyo97";
        user.role = UserRole.ROLE_USER;
        user.refreshToken = refreshToken;
        return user;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
