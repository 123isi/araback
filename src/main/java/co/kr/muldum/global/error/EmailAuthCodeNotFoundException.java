package co.kr.muldum.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailAuthCodeNotFoundException extends RuntimeException {

    public EmailAuthCodeNotFoundException() {
        super("인증 코드를 찾을 수 없습니다.");
    }
    public EmailAuthCodeNotFoundException(String message) {
        super(message);
    }
}
