package co.kr.muldum.domain.notice.model.enums;

import co.kr.muldum.application.notice.NoticeResponseDto;
import co.kr.muldum.domain.notice.exception.InvalidNoticeRequestException;
import co.kr.muldum.domain.notice.service.NoticeService;

public enum Status {
  GENERAL{
    @Override
    public NoticeResponseDto getNoticeById(Long noticeId, NoticeService noticeService, Long teamId) {
      return noticeService.getGeneralNoticeById(noticeId);
    }
  },
  TEAM{
    @Override
    public NoticeResponseDto getNoticeById(Long noticeId, NoticeService noticeService, Long teamId) {
      if (teamId == null) {
        throw new InvalidNoticeRequestException("TEAM 조회에는 teamId가 필요합니다.");
      }
      return noticeService.getTeamNoticeById(noticeId, teamId);
    }
  };

  public abstract NoticeResponseDto getNoticeById(Long noticeId, NoticeService noticeService, Long teamId);
}
