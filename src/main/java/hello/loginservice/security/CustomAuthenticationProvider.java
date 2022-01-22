package hello.loginservice.security;

import hello.loginservice.security.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//TODO 2번
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenUtils tokenUtils;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String userEmail = token.getName();
        String userPw = (String) token.getCredentials();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(userEmail);//여기서 회원이 반환된다.
        if (!passwordEncoder.matches(userPw, userDetails.getPassword()))
            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");/*email에 일치하는 회원은 찾았는데, 패스워드가 일치하지 않으면 예외 발생*/

        /*토큰 박을 때 */
        String accessToken = tokenUtils.createAccessToken(userDetails.getUsername(), userDetails.getNickname(), userDetails.getRole());
        String refreshToken = tokenUtils.createRefreshToken(userDetails.getUsername(), userDetails.getNickname(), userDetails.getRole());

        return new UsernamePasswordAuthenticationToken(userDetails, userPw, userDetails.getAuthorities());
    }

    @Override public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
