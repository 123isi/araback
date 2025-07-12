package co.kr.muldum.presentation.items;

import co.kr.muldum.application.items.*;
import co.kr.muldum.domain.items.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tch/items")
public class TchItemController {

    private final ItemService itemService;
  
    @PostMapping("/delivery-num")
    public ResponseEntity<SimpleMessageResponseDto> registerDeliveryNumber(
            @RequestBody DeliveryNumberRequestDto deliveryNumberRequestDto
    ) {
        itemService.addDeliveryNumber(deliveryNumberRequestDto.itemId(), deliveryNumberRequestDto.deliveryNumber());
        return ResponseEntity.ok(SimpleMessageResponseDto.of("운송장 번호가 등록되었습니다."));
    }
 
    @PostMapping("/reject")
    public ResponseEntity<RejectItemResponseDto> rejectItem(
            @RequestBody RejectItemRequestDto rejectItemRequestDto
    ) {
        itemService.rejectItem(rejectItemRequestDto.itemId(), rejectItemRequestDto.reason());
        return ResponseEntity.ok(RejectItemResponseDto.of());
    }

    @PostMapping("/submit")
    public ResponseEntity<ApproveItemResponseDto> approveItem(
            @RequestBody ApproveItemRequestDto approveItemRequestDto
    ) {
        Long itemId = approveItemRequestDto.getItemId();
        ApproveItemResponseDto response = itemService.approveItem(itemId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit/all")
    public ResponseEntity<ApproveItemResponseDto> approveAllItems() {
        ApproveItemResponseDto response = itemService.approveAllItems();
        return ResponseEntity.ok(response);
    }
}
