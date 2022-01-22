package hello.loginservice.entity;


import lombok.Data;

@Data
public class JoinUser {

    private String email;
    private String password;
    private String nickname;

    public JoinUser(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public JoinUser() {}
}
