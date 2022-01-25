package hello.loginservice.security;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException(String userEmail){
        super(userEmail + " NotFoundException");
    }
}
