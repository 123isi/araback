package co.kr.muldum.infrastructure.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSend implements EmailSender {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Override
    public void send(String to, String code) {
        if ("local".equals(activeProfile)) {
            System.out.println("[로컬] 이메일 전송 생략. 대신 콘솔에 출력합니다.");
            System.out.println("받는 사람: " + to);
            System.out.println("인증 코드: " + code);
            return;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("[물듬] 이메일 인증 코드입니다.");
            helper.setFrom(from);

            String htmlContent =
                    "<div style=\"max-width: 37.5rem; margin: 2rem auto; padding: 2rem;\">"
                            + " <img src=\"https://i.ibb.co/FLmRNsRH/muldum.png\" alt=\"muldum\" style=\"width: 2.8rem; height: 2.5rem;\"> "
                            + " <div style=\"font-size: 1.8rem; font-weight: bold; margin: 1.5rem 0 1rem; color: #000;\">"
                            + "물듬 메일인증 안내"
                            + "</div>"
                            + "<div style=\"font-size: 1rem; line-height: 1.6; margin-bottom: 2rem; color: #000;\">"
                            + "안녕하세요.<br/>"
                            + "물듬을 이용해주셔서 감사합니다.<br/>"
                            + "요청하신 인증 코드는 다음과 같습니다 :"
                            + "</div>"
                            + "<div style=\"background-color: #FAFAFA; padding: 2rem; text-align: center; border-radius: 0.25rem;\">"
                            + "  <div style=\"font-size: 1.5rem; font-weight: bold; margin-bottom: 0.8rem;\">"
                            + code
                            + "  </div>"
                            + "  <div style=\"color: #909090; font-size: 0.8rem;\">"
                            + "    본 코드는 5분간 유효하니, 빠르게 입력해주세요"
                            + "  </div>"
                            + "</div>"
                            + "<div style=\"margin-top: 2rem; font-size: 0.8rem; color: #909090;\">"
                            + "이 메일은 발신 전용입니다."
                            + "</div>"
                            + "</div>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("✅ 이메일 전송 완료 -> To: {}", to);
        } catch (Exception e) {
            log.error("❌ 이메일 전송 실패: {}", e.getMessage());
        }
    }
}
