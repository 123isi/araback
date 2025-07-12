package co.kr.muldum.domain.notice.repository;

import co.kr.muldum.domain.notice.model.NoticeEntity;
import co.kr.muldum.domain.notice.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

  Page<NoticeEntity> findAllByStatus(Status status, Pageable pageable);

  Optional<NoticeEntity> findByIdAndStatus(Long id, Status status);

  Optional<NoticeEntity> findByIdAndStatusAndTeamId(Long id, Status status, Long teamId);
}
