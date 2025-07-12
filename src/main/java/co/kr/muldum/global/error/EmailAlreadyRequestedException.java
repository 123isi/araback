package co.kr.muldum.global.error;

public class EmailAlreadyRequestedException extends RuntimeException {
    public EmailAlreadyRequestedException() {
        super("이미 인증 요청이 진행 중인 이메일입니다.");
    }
}
