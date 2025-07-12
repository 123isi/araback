package co.kr.muldum.application.notice;

import co.kr.muldum.domain.notice.model.enums.Status;
import lombok.Data;

@Data
public class NoticeRequestDto {
  private String title;
  private String content;
  private Long teacherId;
  private Status status;
  private String teacher;
  private String teamId;

  public boolean isTeamNotice() {
    return status == Status.TEAM;
  }

}
