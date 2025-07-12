package co.kr.muldum.application.user;

import co.kr.muldum.domain.user.StudentLoginScopes;
import co.kr.muldum.domain.user.TeacherLoginScopes;
import co.kr.muldum.domain.user.User;
import co.kr.muldum.domain.user.UserRepository;
import co.kr.muldum.global.dto.*;
import co.kr.muldum.global.error.ClientInvalidException;
import co.kr.muldum.global.dto.BsmTokenResponse;
import co.kr.muldum.global.dto.BsmUserResponse;
import co.kr.muldum.global.dto.LoginRequest;
import co.kr.muldum.global.dto.LoginResponse;
import co.kr.muldum.global.error.CodeNotFoundException;
import co.kr.muldum.domain.token.RefreshToken;
import co.kr.muldum.domain.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${bsm.client-id}")
    private String clientId;

    @Value("${bsm.client-secret}")
    private String clientSecret;

    @Value("${bsm.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginResponse loginWithBsm(LoginRequest request) {

        String code = request.getAuthCode();

        String tokenUrl = "https://auth.bssm.kro.kr/oauth/token";

        String requestBody = String.format(
            "client_id=%s&client_secret=%s&code=%s&grant_type=authorization_code&redirect_uri=%s",
            clientId,
            clientSecret,
            code,
            redirectUri
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<BsmTokenResponse> tokenResponse = restTemplate.exchange(
            tokenUrl,
            HttpMethod.POST,
            requestEntity,
            BsmTokenResponse.class
        );

        String accessToken = Optional.ofNullable(tokenResponse.getBody())
                .map(BsmTokenResponse::getAccessToken)
                .orElseThrow(CodeNotFoundException::new);


        if (accessToken == null) {
            throw new RuntimeException("BSM OAuth 서버로부터 access token을 받아오지 못했습니다.");
        }

        String userInfoUrl = "https://auth.bssm.kro.kr/api/v1/user/me";

        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(accessToken);
        HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);

        ResponseEntity<BsmUserResponse> userResponse = restTemplate.exchange(
            userInfoUrl,
            HttpMethod.GET,
            userRequest,
            BsmUserResponse.class
        );

        BsmUserResponse user = userResponse.getBody();
        if (user == null) {
            throw new RuntimeException("사용자 정보를 받아올 수 없습니다.");
        }

        User existingUser = userRepository.findByCode(user.getCode()).orElse(null);

        User finalUser;

        if (existingUser == null) {
            User.Role parsedRole;
            try {
                parsedRole = User.Role.valueOf(user.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ClientInvalidException("올바르지 않은 역할 값입니다: " + user.getRole());
            }

            finalUser = User.builder()
                    .code(user.getCode())
                    .email(user.getEmail())
                    .name(user.getName())
                    .role(parsedRole)
                    .grade(user.getStudent().getGrade())
                    .classNo(user.getStudent().getClassNo())
                    .studentNo(user.getStudent().getStudentNo())
                    .build();
            userRepository.save(finalUser);
        } else {
            finalUser = existingUser;
        }

        List<String> scopeList;
        if ("STUDENT".equals(finalUser.getRole())) {
            scopeList = StudentLoginScopes.SCOPES;
        } else {
            scopeList = TeacherLoginScopes.SCOPES;
        }

        return new LoginResponse(finalUser, scopeList, accessToken, "dummy-refresh-token");
    }

    public TokenResponseDto refreshToken(TokenRequestDto request) {
        if (!clientId.equals(request.getClientId()) || !clientSecret.equals(request.getClientSecret())) {
            throw new ClientInvalidException("유효하지 않은 클라이언트 자격 정보입니다.");
        }

        String tokenUrl = "https://auth.bssm.kro.kr/oauth/token";

        String requestBody = String.format(
            "client_id=%s&client_secret=%s&code=%s&grant_type=authorization_code&redirect_uri=%s",
            clientId,
            clientSecret,
            request.getAuthCode(),
            redirectUri
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<BsmTokenResponse> tokenResponse = restTemplate.exchange(
            tokenUrl,
            HttpMethod.POST,
            requestEntity,
            BsmTokenResponse.class
        );

        String accessToken = Optional.ofNullable(tokenResponse.getBody())
                .map(BsmTokenResponse::getAccessToken)
                .orElseThrow(CodeNotFoundException::new);

        RefreshToken refreshToken = new RefreshToken(request.getAuthCode(), accessToken);
        refreshTokenRepository.save(refreshToken);

        return new TokenResponseDto(accessToken, null);
    }
}
