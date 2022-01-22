package hello.loginservice.entity;


import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class JoinUser {

    @Nullable
    private String email;
    @Nullable
    private String password;
    @Nullable
    private String nickname;

    public JoinUser(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
    public JoinUser() {}
}
