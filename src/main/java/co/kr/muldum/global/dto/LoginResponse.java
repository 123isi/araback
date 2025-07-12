package co.kr.muldum.global.dto;

import co.kr.muldum.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private User user;
    private List<String> scopeList;
    private String UserId;
    private String accessToken;
}
