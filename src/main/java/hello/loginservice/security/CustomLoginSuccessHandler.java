package hello.loginservice.security;

import hello.loginservice.entity.User;
import hello.loginservice.security.jwt.AuthConstants;
import hello.loginservice.security.jwt.TokenUtils;
import hello.loginservice.security.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TokenUtils tokenUtils;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        System.out.println("CustomLoginSuccessHandler.onAuthenticationSuccess");
        final User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        final String accessToken = tokenUtils.createAccessToken(user.getEmail(), user.getNickname(), user.getRole().getValue());
        final String refreshToken = tokenUtils.createAccessToken(user.getEmail(), user.getNickname(), user.getRole().getValue());
        redisService.setValues(user.getEmail() + "-at", accessToken);
        if(redisService.getValues(user.getEmail()+"-rt") == null) redisService.setValues(user.getEmail() + "-rt", refreshToken);
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + accessToken);
    }
}
