package co.kr.muldum.domain.items.repository;

import co.kr.muldum.domain.items.model.ItemRequest;
import co.kr.muldum.domain.items.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findAllByTeamIdAndStatus(Long teamId, Status status);

    List<ItemRequest> findAllByTeamId(Long teamId);

    List<ItemRequest> findAllByStatus(Status status);
}
