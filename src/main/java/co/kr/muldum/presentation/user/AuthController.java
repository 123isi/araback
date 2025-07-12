package co.kr.muldum.presentation.user;

import co.kr.muldum.application.user.AuthService;
import co.kr.muldum.global.dto.LoginRequest;
import co.kr.muldum.global.dto.LoginResponse;
import co.kr.muldum.global.dto.TokenRequestDto;
import co.kr.muldum.global.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/ara/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("로그인 시도: email={}, code={}", request.getEmail(), request.getCode());

        LoginResponse response = authService.loginWithBsm(request);

        log.info("로그인 성공: userId={}, token={}", response.getUserId(), response.getAccessToken());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refreshToken(@RequestBody TokenRequestDto request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
}
