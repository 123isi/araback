package co.kr.muldum.presentation.items;

import co.kr.muldum.application.items.*;
import co.kr.muldum.domain.budget.service.TeamBudgetService;
import co.kr.muldum.domain.items.model.enums.Status;
import co.kr.muldum.domain.items.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("std/items")
public class StdItemController {
    private final ItemService itemService;
    private final TeamBudgetService teamBudgetService;
    @PostMapping("/{team-id}/temp")
    public ResponseEntity<ItemResponseDto> requestItemTemp(
            @PathVariable("team-id") Long teamId,
            @RequestBody ItemRequestDto itemRequestDto
    ) {
        return ResponseEntity.ok(itemService.requestItemTemp(teamId, itemRequestDto));
    }
    @PatchMapping("/{team-id}")
    public ResponseEntity<ItemResponseDto> requestItem(
            @PathVariable("team-id") Long teamId
    ) {
        itemService.submitItems(teamId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{team-id}")
    public ResponseEntity<List<ItemResponseDto>> getItemRequests(
            @PathVariable("team-id") Long teamId
    ) {
        List<ItemResponseDto> items = itemService.viewItems(teamId);
        return ResponseEntity.ok(items);
    }
    @GetMapping("/{team-id}/statuses")
    public ResponseEntity<List<ItemDeliveryStatusDto>> getItemStatuses(
            @PathVariable("team-id") Long teamId
    ) {
        return ResponseEntity.ok(itemService.getItemStatuses(teamId));
    }
    @GetMapping("/{team-id}/temp")
    public ResponseEntity<List<ItemResponseDto>> getTempItems(
            @PathVariable("team-id") Long teamId
    ) {
        List<ItemResponseDto> items = itemService.getItemsByStatus(teamId, Status.INTEMP);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/money/{team-id}")
    public ResponseEntity<BudgetDetailsDto> getRemainingMoney(
            @PathVariable("team-id") Long teamId
    ) {
        return ResponseEntity.ok(teamBudgetService.getBudgetDetails(teamId));
    }
}
