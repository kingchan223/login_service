package hello.loginservice.controller;

import hello.loginservice.entity.JoinUser;
import hello.loginservice.entity.User;
import hello.loginservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class Controller {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository repository;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "로그인 폼입니다.";
    }

//    @PostMapping("/login")
//    public String join(@ModelAttribute LoginUser joinUser) {
//        log.info("사용자 입력 joinUser = {}", joinUser);
//
//    }

    @PostMapping("/join")
    public User join(@RequestBody JoinUser joinUser) {
        log.info("사용자 입력 joinUser = {}", joinUser);
        // -- 서비스로 가야함 --
        String ecp = passwordEncoder.encode(joinUser.getPassword());
        User user = User.makeUser(joinUser.getEmail(), ecp);
        return repository.save(user); // DTO반환 X
    }

    @GetMapping("/user/helloUser")
    public String afterSuccessLogin() {
        return "helloUser your login is successful";
    }
}
