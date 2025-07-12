package co.kr.muldum.application.items;

import co.kr.muldum.domain.items.model.enums.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RejectItemResponseDto {
    private Status status;
    private String message;

    public static RejectItemResponseDto of() {
        return RejectItemResponseDto.builder()
                .status(Status.REJECTED)
                .message("거절 사유가 등록되었습니다.")
                .build();
    }
}
