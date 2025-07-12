package co.kr.muldum.domain.file.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "file") // ← 실제 테이블 이름
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoredFile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String path;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, length = 10)
  private String type;

  @Column(name = "size_bytes", nullable = false)
  private Integer sizeBytes;

  @Column(name = "owner_user_id", nullable = false)
  private Long ownerUserId;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  @Builder
  public StoredFile(String path, String name, String type, Integer sizeBytes, Long ownerUserId) {
    this.path = path;
    this.name = name;
    this.type = type;
    this.sizeBytes = sizeBytes;
    this.ownerUserId = ownerUserId;
  }
}
