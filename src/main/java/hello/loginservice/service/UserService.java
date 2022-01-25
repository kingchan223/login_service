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
        User user = User.makeUser(joinUser.getEmail(), ecp, joinUser.getNickname(), joinUser.getUsername(),joinUser.getSex(), joinUser.getPhone(), joinUser.getBirthday());
        return userRepository.save(user);
    }
}