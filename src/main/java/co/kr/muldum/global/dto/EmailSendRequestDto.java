package co.kr.muldum.global.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendRequestDto {

    @NotBlank(message = "이메일은 비어 있을 수 없습니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;
}
