package co.kr.muldum.infrastructure.email;

public interface EmailSender {
    void send(String to, String code);
}
