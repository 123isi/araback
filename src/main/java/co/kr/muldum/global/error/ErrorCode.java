package co.kr.muldum.global.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    INVALID_CLIENT("C001", "유효하지 않은 클라이언트 자격 정보입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_ROLE("U002", "올바르지 않은 역할 값입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("U003", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    TOKEN_EXPIRED("T001", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
}
