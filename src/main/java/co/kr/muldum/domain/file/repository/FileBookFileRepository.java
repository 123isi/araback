package co.kr.muldum.domain.file.repository;

import co.kr.muldum.domain.file.model.FileBookFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileBookFileRepository extends JpaRepository<FileBookFile, Long> {
}
