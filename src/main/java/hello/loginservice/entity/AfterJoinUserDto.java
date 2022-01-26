package hello.loginservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AfterJoinUserDto {
    private String email;
    private String username; //본명 (진짜 본인 이름)
    private String nickname; //사용할 닉네임
}
