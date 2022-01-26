package hello.loginservice.security;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class RespDto<T> implements Serializable {
    private final int statusCode;
    private final String message;
    private final T data;

    public RespDto(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
