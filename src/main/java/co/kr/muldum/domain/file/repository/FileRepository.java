package co.kr.muldum.domain.file.repository;

import co.kr.muldum.domain.file.model.StoredFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<StoredFile, Long> {
  Optional<StoredFile> findByPath(String path);
  Optional<StoredFile> findByPathAndName(String path, String name);
}
