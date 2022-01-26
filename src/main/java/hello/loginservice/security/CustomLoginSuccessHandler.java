package hello.loginservice.security;

import hello.loginservice.entity.User;
import hello.loginservice.security.jwt.TokenUtils;
import hello.loginservice.security.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static hello.loginservice.security.jwt.AuthConstants.*;

@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TokenUtils tokenUtils;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        String accessToken = tokenUtils.createAccessToken(user.getEmail(), user.getNickname(), user.getRole().toString());
        String refreshToken = tokenUtils.createAccessToken(user.getEmail(), user.getNickname(), user.getRole().toString());

        if(redisService.getValues(user.getEmail()+REDIS_RT) == null){
            redisService.setAccessToken( accessToken, user.getEmail()+REDIS_AT);
            redisService.setRefreshToken( refreshToken, user.getEmail()+REDIS_RT);
        }
        else
            redisService.setAccessToken(user.getEmail() + REDIS_AT, accessToken);

        response.addHeader(AUTH_HEADER, TOKEN_TYPE + SPACE + accessToken);
    }
}
