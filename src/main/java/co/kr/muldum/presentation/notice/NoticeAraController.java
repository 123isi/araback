package co.kr.muldum.presentation.notice;

import co.kr.muldum.application.notice.NoticeSimpleResponseDto;
import co.kr.muldum.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import co.kr.muldum.application.notice.NoticeResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("ara/notice")
public class NoticeAraController {
  private final NoticeService noticeService;

  @GetMapping()
  public ResponseEntity<Page<NoticeSimpleResponseDto>> getAllNotice(
          @PageableDefault(
                  page = 0,
                  size = 12,
                  sort = "createdAt",
                  direction = Sort.Direction.DESC
          ) Pageable pageable
  ) {
    Page<NoticeSimpleResponseDto> noticePage = noticeService.getNotices(pageable);
    return ResponseEntity.ok(noticePage);
  }

  @GetMapping("/{notice_id}")
  public NoticeResponseDto getNoticeById(
          @PathVariable("notice_id") Long noticeId
  ) {
    return noticeService.getGeneralNoticeById(noticeId);
  }

}
