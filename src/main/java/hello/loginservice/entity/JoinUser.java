package hello.loginservice.entity;


import lombok.Data;

@Data
public class JoinUser {
    private String email;
    private String password;

    public JoinUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public JoinUser() {}
}
