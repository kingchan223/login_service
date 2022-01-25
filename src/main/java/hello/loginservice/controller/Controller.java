package hello.loginservice.controller;

import hello.loginservice.entity.JoinUser;
import hello.loginservice.entity.User;
import hello.loginservice.repository.UserRepository;
import hello.loginservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class Controller {

    private final UserRepository repository;
    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    //권한이 없는 사용자라면 해당 api로 redirect 된다.
    @GetMapping("/loginForm")
    public String loginForm() {
        return "로그인 폼입니다.";
    }

//    @PostMapping("/login")
//    public String join(@ModelAttribute LoginUser joinUser) {
//        log.info("사용자 입력 joinUser = {}", joinUser);
//
//    }

    //여기서 회원가입을 완료해도 loginForm 으로 redirect 된다.
    @PostMapping("/join")
    public User join(@RequestBody JoinUser user) {
        //TODO
        /* 클라이언트와 협의하여 어떤 형식을 보내면 /login을 보내는 건지 정하고 그 응답을 보내자. */
        return userService.join(user);
    }

    @GetMapping("/user/helloUser")
    public String afterSuccessLogin() {
        return "helloUser your login is successful you have authorization";
    }

    @GetMapping("/error/unauthorized")
    public String noToken() {
        return "No Token~";
    }

}
