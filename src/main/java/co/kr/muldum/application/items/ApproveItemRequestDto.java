package co.kr.muldum.application.items;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApproveItemRequestDto {
    @NotNull(message = "물품 ID는 필수입니다.")
    private Long itemId;
}
