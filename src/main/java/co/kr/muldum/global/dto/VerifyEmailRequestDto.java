package co.kr.muldum.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyEmailRequestDto {
    private String email;
    private String code;
}
