package co.kr.muldum.application.email;

import co.kr.muldum.domain.email.EmailAuthCode;
import co.kr.muldum.domain.email.EmailAuthCodeRepository;
import co.kr.muldum.infrastructure.email.EmailSender;
import co.kr.muldum.global.error.EmailAlreadyRequestedException;
import java.util.Optional;
import co.kr.muldum.global.error.EmailAuthCodeNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class EmailUseCase {

    private final EmailSender emailSender;
    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${app.email.allowed-domain:@bssm.hs.kr}")
    private String allowedDomain;


    /**
     * 이메일 인증 코드 발송
     *
     * @param email 인증 코드를 발송할 이메일 주소
     * @throws EmailAlreadyRequestedException 이미 인증 코드가 요청된 이메일 주소인 경우
     */
    public void send(String email) {
        if (!email.endsWith(allowedDomain)) {
            throw new IllegalArgumentException("잘못된 이메일 도메인입니다.");
        }
        String code = generateRandomCode();
        EmailAuthCode authCode = new EmailAuthCode(email, code, LocalDateTime.now().plusMinutes(5));
        emailAuthCodeRepository.save(authCode);
        emailSender.send(email, code);
    }

    /**
     * 인증 코드 생성
     *
     * @return 6자리 랜덤 숫자 문자열
     */
    private String generateRandomCode() {
        String randomedCode = String.valueOf(secureRandom.nextInt(900000) + 100000);
        return randomedCode;
    }

    /**
     * 이메일 인증 코드 검증
     *
     * @param email 이메일 주소
     * @param code 인증 코드
     * @throws EmailAuthCodeNotFoundException 유효한 인증 코드가 없는 경우
     */
    public void verify(String email, String code) {
        Optional<EmailAuthCode> optional = emailAuthCodeRepository
            .findTopByEmailOrderByExpiresAtDesc(email);
        EmailAuthCode authCode = optional
            .filter(ec -> ec.getCode().equals(code))
            .orElseThrow(() -> new EmailAuthCodeNotFoundException("유효한 인증 코드가 없습니다."));
        if (authCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new EmailAuthCodeNotFoundException("인증 코드가 만료되었습니다.");
        }
        authCode.setVerified(true);
        emailAuthCodeRepository.save(authCode);
    }
}
