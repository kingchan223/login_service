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

import java.io.PrintWriter;

//TODO 2번
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenUtils tokenUtils;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("-----------CustomAuthenticationProvider.attemptAuthentication------------");
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String userEmail = token.getName();
        String userPw = (String) token.getCredentials();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(userEmail);//여기서 회원이 반환된다.
        if (userDetails == null){
            log.error("invalid email");
            throw new UsernameNotFoundException(userDetails.getUsername()+"Invalid password");
        }
        if (!passwordEncoder.matches(userPw, userDetails.getPassword())){
            log.error("invalid password = {}", userDetails.getUsername());
            throw new BadCredentialsException(userDetails.getUsername()+"Invalid password");
        }

        /*토큰 박을 때 */
        String accessToken = tokenUtils.createAccessToken(userDetails.getUsername(), userDetails.getNickname(), userDetails.getRole());
        String refreshToken = tokenUtils.createRefreshToken(userDetails.getUsername(), userDetails.getNickname(), userDetails.getRole());

        return new UsernamePasswordAuthenticationToken(userDetails, userPw, userDetails.getAuthorities());
    }



    @Override public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
