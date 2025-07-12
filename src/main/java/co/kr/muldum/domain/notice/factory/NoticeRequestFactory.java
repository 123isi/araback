package co.kr.muldum.domain.notice.factory;


import co.kr.muldum.application.notice.CreateNoticeRequestDto;
import co.kr.muldum.domain.notice.model.NoticeEntity;
import co.kr.muldum.domain.notice.model.enums.Status;

public class NoticeRequestFactory {
  private NoticeRequestFactory() {}

  public static NoticeEntity createGeneralNotice(CreateNoticeRequestDto dto, Long teacher) {
    return NoticeEntity.builder()
            .title(dto.getTitle())
            .content(dto.getContent())
            .status(Status.GENERAL)
            .userId(teacher)
            .build();
  }

  public static NoticeEntity createTeamNotice(CreateNoticeRequestDto dto, Long teacher) {
    return NoticeEntity.builder()
            .title(dto.getTitle())
            .content(dto.getContent())
            .status(Status.TEAM)
            .userId(teacher)
            .teamId(dto.getTeamId())
            .build();
  }
}
