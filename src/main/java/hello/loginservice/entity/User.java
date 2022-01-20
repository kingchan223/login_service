package hello.loginservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ToString
@NoArgsConstructor(access= AccessLevel.PROTECTED)/*프록시 객체를 위해 protect로 기본 생성자*/
@Getter
@Entity
public class User extends DateBaseEntity{
    @Id @GeneratedValue
    private Long id;

    private String email;
    private String password;

    private String username;
    private String sex;//F, M
    private String phone;//'-'없이
    private String birthday;//생년월일 7자리
    private String nickname;
    private String role;

    public static User makeUser(String email, String password) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.username = "이찬영";
        user.sex = "F";
        user.phone = "01088881111";
        user.birthday = "970223";
        user.nickname = "kokiyo97";
        user.role = "user";
        return user;
    }
}
