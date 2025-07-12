package co.kr.muldum.infrastructure.email;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmailSend.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class EmailSendDiffblueTest {
    @Autowired
    private EmailSend emailSend;

    @MockitoBean
    private JavaMailSender javaMailSender;

    /**
     * Test {@link EmailSend#send(String, String)}.
     * <p>
     * Method under test: {@link EmailSend#send(String, String)}
     */
    @Test
    @DisplayName("Test send(String, String)")
    @Tag("MaintainedByDiffblue")
    void testSend() {
        // Arrange
        JavaMailSenderImpl mailSender = mock(JavaMailSenderImpl.class);
        when(mailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));

        // Act
        new EmailSend(mailSender).send("alice.liddell@example.org", "Code");

        // Assert
        verify(mailSender).createMimeMessage();
    }

    /**
     * Test {@link EmailSend#send(String, String)}.
     * <ul>
     *   <li>Given {@link JavaMailSenderImpl} {@link JavaMailSenderImpl#createMimeMessage()} return {@link MimeMessage}.</li>
     * </ul>
     * <p>
     * Method under test: {@link EmailSend#send(String, String)}
     */
    @Test
    @DisplayName("Test send(String, String); given JavaMailSenderImpl createMimeMessage() return MimeMessage")
    @Tag("MaintainedByDiffblue")
    void testSend_givenJavaMailSenderImplCreateMimeMessageReturnMimeMessage() throws MessagingException {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        doNothing().when(mimeMessage).setContent(Mockito.<Multipart>any());
        JavaMailSenderImpl mailSender = mock(JavaMailSenderImpl.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        new EmailSend(mailSender).send("❌ 이메일 전송 실패: {}", "Code");

        // Assert
        verify(mimeMessage).setContent(isA(Multipart.class));
        verify(mailSender).createMimeMessage();
    }

    /**
     * Test {@link EmailSend#send(String, String)}.
     * <ul>
     *   <li>Given {@link JavaMailSender} {@link JavaMailSender#send(MimeMessage)} does nothing.</li>
     *   <li>Then calls {@link JavaMailSender#send(MimeMessage)}.</li>
     * </ul>
     * <p>
     * Method under test: {@link EmailSend#send(String, String)}
     */
    @Test
    @DisplayName("Test send(String, String); given JavaMailSender send(MimeMessage) does nothing; then calls send(MimeMessage)")
    @Tag("MaintainedByDiffblue")
    void testSend_givenJavaMailSenderSendDoesNothing_thenCallsSend() throws MailException {
        // Arrange
        doNothing().when(javaMailSender).send(Mockito.<MimeMessage>any());
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));

        // Act
        emailSend.send("alice.liddell@example.org", "Code");

        // Assert
        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(isA(MimeMessage.class));
    }

    /**
     * Test {@link EmailSend#send(String, String)}.
     * <ul>
     *   <li>Given {@link MimeMessage} {@link Message#setRecipient(RecipientType, Address)} does nothing.</li>
     *   <li>Then calls {@link Message#setRecipient(RecipientType, Address)}.</li>
     * </ul>
     * <p>
     * Method under test: {@link EmailSend#send(String, String)}
     */
    @Test
    @DisplayName("Test send(String, String); given MimeMessage setRecipient(RecipientType, Address) does nothing; then calls setRecipient(RecipientType, Address)")
    @Tag("MaintainedByDiffblue")
    void testSend_givenMimeMessageSetRecipientDoesNothing_thenCallsSetRecipient()
            throws MessagingException, MailException {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        doNothing().when(mimeMessage).setRecipient(Mockito.<RecipientType>any(), Mockito.<Address>any());
        doNothing().when(mimeMessage).setContent(Mockito.<Multipart>any());
        doNothing().when(mimeMessage).setFrom(Mockito.<Address>any());
        doNothing().when(mimeMessage).setSubject(Mockito.<String>any(), Mockito.<String>any());
        doNothing().when(javaMailSender).send(Mockito.<MimeMessage>any());
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        emailSend.send("alice.liddell@example.org", "Code");

        // Assert
        verify(mimeMessage).setRecipient(isA(RecipientType.class), isA(Address.class));
        verify(mimeMessage).setContent(isA(Multipart.class));
        verify(mimeMessage).setFrom(isA(Address.class));
        verify(mimeMessage).setSubject(eq("[물듬] 이메일 인증 코드입니다."), eq("UTF-8"));
        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(isA(MimeMessage.class));
    }
}
