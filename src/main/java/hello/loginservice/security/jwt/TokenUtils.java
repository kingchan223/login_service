package hello.loginservice.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import hello.loginservice.entity.User;
import hello.loginservice.entity.UserRole;
import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static hello.loginservice.security.jwt.JwtProperties.*;

@Slf4j
@Component/**/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenUtils {

    private static final String secretKey = SECRET;

    public static String generateJwtToken(User user) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getEmail())
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setExpiration(createExpireDateForOneYear())
                .signWith(SignatureAlgorithm.HS256, createSigningKey());

        return builder.compact();
    }

    public String createAccessToken(String email, String nickname, String role){
//       return JWT.create()
//                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_EXPIRED_TIME))
//                .withIssuer("LEEE")
//                .withClaim("email", email)
//                .withClaim("nickname", nickname)
//                .withClaim("role", role)
//                .sign(Algorithm.HMAC512(ACCESS_SECRET));

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(email+"/"+nickname+"/"+role )
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + ACCESS_EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String createRefreshToken(String email, String nickname, String role){
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(email+"/"+nickname+"/"+role )
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + REFRESH_EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    public String get(String token, String key){
        if(key.equals("email"))
            return getUserEmailFromToken(token);
        else if(key.equals("role"))
            return getRoleFromToken(token);
        else return null;
    }

    private static Date createExpireDateForOneYear() {
        // 토큰 만료시간은 30일으로 설정
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        return c.getTime();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private static Map<String, Object> createClaims(User user) {
        // 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        return claims;
    }

    private static Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private static Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
    }

    private static String getUserEmailFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        String sub = (String) claims.get("sub");
        return sub.split("/")[0];

    }

    private static String getRoleFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return claims.get("role").toString();
    }
}
