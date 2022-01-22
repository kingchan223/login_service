package hello.loginservice.service;

import hello.loginservice.entity.JoinUser;
import hello.loginservice.entity.User;
import hello.loginservice.entity.UserRole;
import hello.loginservice.repository.UserRepository;
import hello.loginservice.security.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenUtils tokenUtils;

    @Transactional
    public User join(JoinUser joinUser) {
        //유효성 검사 된거라고 가정
        String ecp = passwordEncoder.encode(joinUser.getPassword());
        String refreshToken = tokenUtils.createRefreshToken(
                joinUser.getEmail(),
                joinUser.getNickname(),
                UserRole.ROLE_USER.getValue()
        );//회원가입은 무조건 ROLE_USER

        User user = User.makeUser(joinUser.getEmail(), ecp, refreshToken);
        return userRepository.save(user);
    }
}
