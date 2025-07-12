package co.kr.muldum.application.notice;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoticeRequestDto {
  private String title;
  private String content;
  private List<AttachmentDto> attachments;
  private String status; // "GENERAL" or "TEAM"

  @Column(name = "team_id")
  private Long teamId;
  private String name;

  @Getter
  @NoArgsConstructor
  public static class AttachmentDto {
    private String url;
  }

  public boolean isTeamNotice() {
    return "TEAM".equalsIgnoreCase(status);
  }

  public boolean isGeneralNotice() {
    return "GENERAL".equalsIgnoreCase(status);
  }
}
