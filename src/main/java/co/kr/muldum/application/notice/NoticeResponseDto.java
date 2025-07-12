package co.kr.muldum.application.notice;

import co.kr.muldum.domain.notice.model.NoticeEntity;
import co.kr.muldum.global.dto.FileInfoDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record NoticeResponseDto(
        Long id, String message, String title, String content, List<FileInfoDto> files, Long teacherId,
        String teacherName, String status, String createdAt
) {

  public static NoticeResponseDto from(NoticeEntity noticeEntity) {
    Long teacherId = null;
    String teacherName = null;

    if (noticeEntity.getUserId() != null) {
      teacherId = noticeEntity.getUserId();
      teacherName = "최병준"; // User 엔티티에 getName() 있어야 함
    }

    String formattedCreatedAt = null;
    if (noticeEntity.getCreatedAt() != null) {
      formattedCreatedAt = noticeEntity.getCreatedAt()
              .atOffset(ZoneOffset.UTC)
              .format(DateTimeFormatter.ISO_INSTANT); // 2025-04-10T09:00:00Z
    }

    // 파일 정보를 별도 DTO로 변환
    List<FileInfoDto> files = null;
    if (noticeEntity.getFileBook() != null &&
            noticeEntity.getFileBook().getFileBookFiles() != null) {
      files = noticeEntity.getFileBook().getFileBookFiles().stream()
              .map(fileBookFile -> new FileInfoDto(
                      fileBookFile.getFile().getId(),
                      fileBookFile.getFile().getName(),
                      fileBookFile.getFile().getPath(),
                      fileBookFile.getFile().getType(),
                      fileBookFile.getFile().getSizeBytes()
              ))
              .collect(Collectors.toList());
    }

    return new NoticeResponseDto(
            noticeEntity.getId(),
            null, // message 필드가 필요할 떄만 매핑
            noticeEntity.getTitle(),
            noticeEntity.getContent(),
            files,
            teacherId,
            teacherName,
            noticeEntity.getStatus().name(),
            formattedCreatedAt
    );
  }
}

