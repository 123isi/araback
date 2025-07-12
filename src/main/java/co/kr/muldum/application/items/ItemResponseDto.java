package co.kr.muldum.application.items;

import co.kr.muldum.domain.items.model.ItemRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponseDto {

    private Long id;
    private Long teamId;
    private String productName;
    private int quantity;
    private Integer price;
    private String productLink;
    private String itemSource;
    private String status;
    private String reason;
    private Integer deliveryNum;

    public static ItemResponseDto from(ItemRequest item) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .teamId(item.getTeamId())
                .productName(item.getProductName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .productLink(item.getProductLink())
                .itemSource(item.getItemSource())
                .status(item.getStatus().name())
                .reason(item.getReason())
                .deliveryNum(item.getDeliveryNum())
                .build();
    }
}
