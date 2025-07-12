package co.kr.muldum.application.items;

import co.kr.muldum.domain.items.model.enums.Status;
import co.kr.muldum.domain.items.model.ItemRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDeliveryStatusDto {
    private String productName;
    private int quantity;
    private Status status;
    private Integer deliveryNum;

    public static ItemDeliveryStatusDto from(ItemRequest item) {
        return ItemDeliveryStatusDto.builder()
                .productName(item.getProductName())
                .quantity(item.getQuantity())
                .status(item.getStatus())
                .deliveryNum(item.getDeliveryNum())
                .build();
    }
}
