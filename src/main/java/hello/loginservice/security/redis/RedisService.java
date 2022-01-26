package hello.loginservice.security.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate redisTemplate;

    public void setAccessToken(String token, String email){
        System.out.println("make access token of "+email);
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(email, token, Duration.ofMinutes(3));
    }

    public void setRefreshToken(String token, String email){
        System.out.println("make refresh token of "+email);
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(email, token, Duration.ofDays(3));
    }

    public String getValues(String email){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        System.out.println("getValues : "+ values.get(email));
        return values.get(email);
    }

    public void delValues(String token) {
        redisTemplate.delete(token.substring(7));
    }
}
