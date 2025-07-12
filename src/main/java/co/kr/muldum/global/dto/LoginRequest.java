package co.kr.muldum.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String clientId;
    private String clientSecret;
    private String authCode;
    private String email;
    private String code;
}
