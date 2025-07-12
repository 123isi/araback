package co.kr.muldum.application.items;

public record DeliveryNumberRequestDto(
        Long itemId,
        String deliveryNumber
) { }
