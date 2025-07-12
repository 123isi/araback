package co.kr.muldum.domain.notice.service;

import co.kr.muldum.application.notice.CreateNoticeRequestDto;
import co.kr.muldum.application.notice.NoticeSimpleResponseDto;
import co.kr.muldum.application.notice.NoticeResponseDto;
import co.kr.muldum.domain.file.repository.FileBookFileRepository;
import co.kr.muldum.domain.file.repository.FileBookRepository;
import co.kr.muldum.domain.file.repository.FileRepository;
import co.kr.muldum.domain.file.service.FileStorageService;
import co.kr.muldum.domain.notice.exception.NoticeNotFoundException;
import co.kr.muldum.domain.notice.factory.NoticeRequestFactory;
import co.kr.muldum.domain.file.model.FileBook;
import co.kr.muldum.domain.file.model.StoredFile;
import co.kr.muldum.domain.file.model.FileBookFile;
import co.kr.muldum.domain.notice.model.NoticeEntity;
import co.kr.muldum.domain.notice.model.enums.Status;
import co.kr.muldum.domain.notice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
  private final NoticeRepository noticeRepository;
  private final FileBookRepository fileBookRepository;
  private final FileRepository fileRepository;
  private final FileBookFileRepository fileBookFileRepository;
  private final FileStorageService fileStorageService;

  @Transactional
  public Long registerNotice(CreateNoticeRequestDto createNoticeRequestDto, Long teacherId) {
    NoticeEntity noticeEntity;

    // 공지 생성
    noticeEntity = createNoticeRequestDto.isTeamNotice()
            ? NoticeRequestFactory.createTeamNotice(createNoticeRequestDto, teacherId)
            : NoticeRequestFactory.createGeneralNotice(createNoticeRequestDto, teacherId);

    // 파일 처리 - AttachmentDto 사용
    if (createNoticeRequestDto.getAttachments() != null && !createNoticeRequestDto.getAttachments().isEmpty()) {
      FileBook fileBook = new FileBook();
      fileBookRepository.save(fileBook);

      for (CreateNoticeRequestDto.AttachmentDto attachment : createNoticeRequestDto.getAttachments()) {
        // URL을 통해 기존 파일 엔티티 조회
        StoredFile existingFile = fileRepository.findByPath(attachment.getUrl())
                .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다: " + attachment.getUrl()));

        fileBookFileRepository.save(new FileBookFile(fileBook, existingFile));
      }

      noticeEntity.setStoredFile(fileBook);
    }

    // 공지 저장
    noticeRepository.save(noticeEntity);
    return noticeEntity.getId();
  }


  public Page<NoticeSimpleResponseDto> getNotices(Pageable pageable) {
    return noticeRepository.findAllByStatus(Status.GENERAL, pageable)
            .map(NoticeSimpleResponseDto::fromEntity);
  }

  public NoticeResponseDto getGeneralNoticeById(Long noticeId) {
    return getNoticeById(noticeId, null, Status.GENERAL);
  }

  public NoticeResponseDto getTeamNoticeById(Long noticeId, Long teamId) {
    return getNoticeById(noticeId, teamId, Status.TEAM);
  }

  private NoticeResponseDto getNoticeById(Long noticeId, Long teamId, Status status) {
    NoticeEntity notice;

    if (status == Status.GENERAL) {
      notice = noticeRepository.findByIdAndStatus(noticeId, status)
              .orElseThrow(() -> new NoticeNotFoundException("공지를 찾을 수 없습니다."));
    } else if (status == Status.TEAM) {
      notice = noticeRepository.findByIdAndStatusAndTeamId(noticeId, status, teamId)
              .orElseThrow(() -> new NoticeNotFoundException("공지를 찾을 수 없습니다."));
    } else {
      throw new IllegalArgumentException("지원하지 않는 공지 유형입니다: " + status);
    }

    return NoticeResponseDto.from(notice);
  }

  public void deleteNotice(Long noticeId) {
  NoticeEntity notice = noticeRepository.findById(noticeId)
          .orElseThrow(() -> new NoticeNotFoundException("공지사항을 찾을 수 없습니다."));

    noticeRepository.deleteById(noticeId);
  }
}
