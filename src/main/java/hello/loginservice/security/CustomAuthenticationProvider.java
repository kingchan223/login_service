package hello.loginservice.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.loginservice.security.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(token.getName());

        if (userDetails == null){
            log.error("invalid email");
            throw new UsernameNotFoundException(userDetails.getUsername()+"Invalid password");
        }
        if (!passwordEncoder.matches((String) token.getCredentials(), userDetails.getPassword())){
            log.error("invalid password = {}", userDetails.getUsername());
            throw new BadCredentialsException(userDetails.getUsername()+"Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, (String) token.getCredentials(), userDetails.getAuthorities());
    }

    @Override public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
