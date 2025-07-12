package co.kr.muldum.application.items;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimpleMessageResponseDto {
    private String message;

    public static SimpleMessageResponseDto of(String message) {
        return SimpleMessageResponseDto.builder()
                .message(message)
                .build();
    }
}
