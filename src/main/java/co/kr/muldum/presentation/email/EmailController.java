package co.kr.muldum.presentation.email;

import co.kr.muldum.application.email.EmailUseCase;
import co.kr.muldum.global.dto.EmailSendRequestDto;
import co.kr.muldum.global.dto.MessageResponseEto;
import co.kr.muldum.global.dto.VerifyEmailRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ara/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailUseCase emailUseCase;

    /**
     * 이메일 인증 코드 요청 API
     *
     * @param emailSendRequestDto 이메일 주소를 포함한 요청 DTO
     * @return 인증 코드 전송 성공 메시지
     */
    @PostMapping
    public ResponseEntity<MessageResponseEto> sendEmailCode(
        @RequestBody EmailSendRequestDto emailSendRequestDto
    ) {
        emailUseCase.send(emailSendRequestDto.getEmail());
        return ResponseEntity.ok(new MessageResponseEto("인증 코드가 전송되었습니다."));
    }

    @PostMapping("/check")
    public ResponseEntity<MessageResponseEto> verifyEmailCode(
        @RequestBody VerifyEmailRequestDto dto
    ) {
        emailUseCase.verify(dto.getEmail(), dto.getCode());
        return ResponseEntity.ok(new MessageResponseEto("이메일 인증이 완료되었습니다."));
    }
}
