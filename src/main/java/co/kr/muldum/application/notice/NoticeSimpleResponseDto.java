package co.kr.muldum.application.notice;

import co.kr.muldum.domain.notice.model.NoticeEntity;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class NoticeSimpleResponseDto {

  private final Long id;
  private final String title;
  private final LocalDateTime createdAt;
  private final String teacher;

  public NoticeSimpleResponseDto(Long id, String title, LocalDateTime createdAt, String teacher) {
    this.id = id;
    this.title = title;
    this.createdAt = createdAt;
    this.teacher = teacher;
  }

  public static NoticeSimpleResponseDto fromEntity(NoticeEntity entity){
    return new NoticeSimpleResponseDto(
            entity.getId(),
            entity.getTitle(),
            entity.getCreatedAt(),
            entity.getUserId()!= null ? "최병준" : "최병준"
    );
  }
}
