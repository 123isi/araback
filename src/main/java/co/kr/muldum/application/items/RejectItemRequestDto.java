package co.kr.muldum.application.items;

import jakarta.validation.constraints.NotNull;

public record RejectItemRequestDto (
    @NotNull(message = "물품 ID는 필수입니다.")
    Long itemId,
    @NotNull(message = "거절 사유는 필수입니다.")
    String reason
) { }
