package co.kr.muldum.domain.email;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface EmailAuthCodeRepository extends CrudRepository<EmailAuthCode, Long> {
    boolean existsByEmailAndVerifiedFalse(String email);

    Optional<EmailAuthCode> findTopByEmailOrderByExpiresAtDesc(String email);
}
