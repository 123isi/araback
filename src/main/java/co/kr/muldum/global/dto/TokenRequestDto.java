package co.kr.muldum.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDto {
    private String clientId;
    private String clientSecret;
    private String authCode;
}
