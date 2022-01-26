package hello.loginservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.loginservice.entity.JoinUser;
import hello.loginservice.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

//TODO 1번
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super.setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("-----------CustomAuthenticationFilter.attemptAuthentication------------");
        JoinUser userInfo = getUserInfo(request);

        assert userInfo != null;
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userInfo.getEmail(), userInfo.getPassword());
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private JoinUser getUserInfo(HttpServletRequest request){
        JoinUser joinUser = null;
        try {
            ServletInputStream ins = request.getInputStream();
            String json = StreamUtils.copyToString(ins, StandardCharsets.UTF_8);
            joinUser = objectMapper.readValue(json, JoinUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return joinUser;//TODO
    }
}
