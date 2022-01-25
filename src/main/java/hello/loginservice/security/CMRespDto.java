package hello.loginservice.security;

import java.io.Serializable;

public class CMRespDto<T> implements Serializable {
    private int exceptionCode;
    private String message;
    private T data;

    public CMRespDto(int exceptionCode, String message, T data) {
        this.exceptionCode = exceptionCode;
        this.message = message;
        this.data = data;
    }
}
