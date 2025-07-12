package co.kr.muldum.global.error;

public class ClientInvalidException extends RuntimeException {
    public ClientInvalidException(String message) {
        super(message);
    }
}
