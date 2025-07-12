package co.kr.muldum.application.email;

import co.kr.muldum.domain.email.EmailAuthCode;
import co.kr.muldum.domain.email.EmailAuthCodeRepository;
import co.kr.muldum.global.error.EmailAlreadyRequestedException;
import co.kr.muldum.infrastructure.email.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final EmailSender emailSender;

    /**
     * 이메일 인증 코드 발송 서비스
     *
     * @param email 인증 코드를 발송할 이메일 주소
     * @throws EmailAlreadyRequestedException 이미 인증 코드가 요청된 이메일 주소인 경우
     */
    public void sendEmailVerificationCode(String email) {

        if (isDuplicateRequest(email)) {
            throw new EmailAlreadyRequestedException();
        }

        String code = generateCode();
        log.info("Generated email verification code for {}: {}", email, code);

        emailSender.send(email, code);

        EmailAuthCode emailAuthCode = new EmailAuthCode(
            null,
            email,
            code,
            false,
            null
        );

        emailAuthCodeRepository.save(emailAuthCode);
    }

    /**
     * 이메일 인증 코드 중복 요청 여부 확인
     *
     * @param email 인증 코드를 요청한 이메일 주소
     * @return 중복 요청 여부
     */
    private boolean isDuplicateRequest(String email) {
        return emailAuthCodeRepository.existsByEmailAndVerifiedFalse(email);
    }

    private String generateCode() {
        int code = (int)(Math.random() * 900_000) + 100_000;
        return String.valueOf(code);
    }
}
