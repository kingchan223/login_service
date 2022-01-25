package hello.loginservice.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final TokenUtils tokenUtils;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws IOException {
        final String header = request.getHeader(AuthConstants.AUTH_HEADER);
        if (header != null) {
            System.out.println("JwtTokenInterceptor.preHandle");
            final String token = TokenUtils.getTokenFromHeader(header);
            if (tokenUtils.isValidToken(token)) return true;
        }
        response.sendRedirect("/error/unauthorized");
        return false;
    }
}
