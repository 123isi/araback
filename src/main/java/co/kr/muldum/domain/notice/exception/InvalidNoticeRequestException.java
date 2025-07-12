package co.kr.muldum.domain.notice.exception;

public class InvalidNoticeRequestException extends RuntimeException {
  public InvalidNoticeRequestException(String message) {
    super(message);
  }
}
