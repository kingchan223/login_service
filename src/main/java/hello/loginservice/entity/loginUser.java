package hello.loginservice.entity;


import lombok.Data;

@Data
public class loginUser {
    private String email;
    private String password;

    public void makeNewUser(String email, String password){


    }
}
