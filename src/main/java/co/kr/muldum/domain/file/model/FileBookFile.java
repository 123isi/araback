package co.kr.muldum.domain.file.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Getter
public class FileBookFile {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "filebook_id")
  private FileBook fileBook;

  @ManyToOne
  @JoinColumn(name = "file_id")
  private StoredFile file;

  public FileBookFile(FileBook fileBook, StoredFile file) {
    this.fileBook = fileBook;
    this.file = file;
  }
}
