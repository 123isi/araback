package co.kr.muldum.presentation.notice;

import co.kr.muldum.application.notice.CreateNoticeRequestDto;
import co.kr.muldum.application.notice.CreateNoticeResponseDto;
import co.kr.muldum.application.notice.NoticeResponseDto;
import co.kr.muldum.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("tch/notice")
public class NoticeTeacherController {
  private final NoticeService noticeService;

  @PostMapping()
  public ResponseEntity<CreateNoticeResponseDto> createNotice(
          @RequestBody CreateNoticeRequestDto createNoticeRequestDto
  ) {
    Long teacherId = 1001L; //하드 코딩된 teacherId, 실제로는 @AuthenticationPrincipal을 통해 가져와야 함
    Long noticeId = noticeService.registerNotice(createNoticeRequestDto, teacherId);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(CreateNoticeResponseDto.builder()  // new 키워드 제거
                    .id(noticeId)
                    .message("공지사항이 등록되었습니다.")
                    .build());
  }

  @DeleteMapping("/{noticeId}")
  public ResponseEntity<NoticeResponseDto> deleteNotice(
          @PathVariable Long noticeId
  ) {
    noticeService.deleteNotice(noticeId);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(new NoticeResponseDto(noticeId, "공지사항을 삭제했습니다!", null, null, null, null, null, null, null));
  }

}
