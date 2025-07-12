package co.kr.muldum.global.error;

public class CodeNotFoundException extends RuntimeException {
    public CodeNotFoundException() {
        super("인증 코드를 찾을 수 없습니다");
    }
}
