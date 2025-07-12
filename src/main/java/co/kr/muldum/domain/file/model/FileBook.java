package co.kr.muldum.domain.file.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file_book")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FileBook {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 역방향 매핑은 선택 사항 (필요할 경우)
  @OneToMany(mappedBy = "fileBook", cascade = CascadeType.ALL, orphanRemoval = true)
  private java.util.List<FileBookFile> fileBookFiles = new java.util.ArrayList<>();

  // 필요 시 Notice와 양방향 매핑할 수도 있음
}
