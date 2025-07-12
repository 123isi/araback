package co.kr.muldum.domain.items.factory;

import co.kr.muldum.application.items.ItemRequestDto;
import co.kr.muldum.domain.items.model.ItemRequest;
import co.kr.muldum.domain.items.model.enums.Status;

public class ItemRequestFactory {

    private ItemRequestFactory() {}

    public static ItemRequest createTempRequest(Long teamId, ItemRequestDto dto) {
        return ItemRequest.builder()
                .teamId(teamId)
                .productName(dto.getProductName())
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .itemSource(dto.getItemSource())
                .reason(dto.getReason())
                .status(Status.INTEMP)
                .build();
    }

    public static ItemRequest withUpdatedDeliveryNumber(ItemRequest item, int i) {
        return item.toBuilder()
                .deliveryNum(i)
                .build();
    }

    public static ItemRequest rejectWithReason(ItemRequest item, String reason) {
        return item.toBuilder()
                .status(Status.REJECTED)
                .reason(reason)
                .deliveryNum(item.getDeliveryNum())
                .build();
    }
}
