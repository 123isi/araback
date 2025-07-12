package co.kr.muldum.application.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateNoticeResponseDto {
  private Long id;
  private String message;

  @Builder
  public CreateNoticeResponseDto(Long id, String message) {
    this.id = id;
    this.message = message;
  }
}
