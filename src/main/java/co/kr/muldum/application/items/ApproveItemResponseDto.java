package co.kr.muldum.application.items;

import co.kr.muldum.domain.items.model.enums.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApproveItemResponseDto {
    private Status status;
    private String message;

    public static ApproveItemResponseDto of() {
        return ApproveItemResponseDto.builder()
                .status(Status.APPROVED)
                .message("물품이 승인되었습니다.")
                .build();
    }
}
