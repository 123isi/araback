package co.kr.muldum.domain.notice.model;

import co.kr.muldum.domain.file.model.FileBook;
import co.kr.muldum.domain.notice.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
public class NoticeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String content;

  @Enumerated(EnumType.STRING)
  private Status status;

  private Long userId;

  @CreatedDate
  private LocalDateTime createdAt;

  private Long teamId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "filebook_id")
  private FileBook fileBook;

  public void setStoredFile(FileBook fileBook) {
    this.fileBook = fileBook;
  }

}

