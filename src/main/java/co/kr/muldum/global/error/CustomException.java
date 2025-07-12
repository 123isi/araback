package co.kr.muldum.global.error;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // 예외 메시지를 ErrorCode로부터 받음
        this.errorCode = errorCode;
    }
}
