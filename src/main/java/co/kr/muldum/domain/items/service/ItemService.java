package co.kr.muldum.domain.items.service;

import co.kr.muldum.application.items.*;
import co.kr.muldum.domain.items.model.ItemRequest;
import co.kr.muldum.domain.items.model.enums.Status;
import co.kr.muldum.domain.items.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import co.kr.muldum.domain.items.factory.ItemRequestFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemResponseDto requestItemTemp(Long teamId, ItemRequestDto itemRequestDto) {
        ItemRequest item = ItemRequestFactory.createTempRequest(teamId, itemRequestDto);
        ItemRequest saved = itemRepository.save(item);

        return ItemResponseDto.from(saved);
    }

    @Transactional
    public void submitItems(Long teamId) {
        List<ItemRequest> tempItems = itemRepository.findAllByTeamIdAndStatus(teamId, Status.INTEMP);

        for (ItemRequest item : tempItems) {
            item.changeIntempToPending();
        }

        itemRepository.saveAll(tempItems);
    }

    public List<ItemResponseDto> viewItems(Long teamId) {
        List<ItemRequest> items = itemRepository.findAllByTeamId(teamId);

        return items.stream()
                .map(ItemResponseDto::from)
                .toList();
    }

    public List<ItemDeliveryStatusDto> getItemStatuses(Long teamId) {
        List<ItemRequest> items = itemRepository.findAllByTeamId(teamId);
        return items.stream()
                .map(ItemDeliveryStatusDto::from)
                .toList();
    }

    public List<ItemResponseDto> getItemsByStatus(Long teamId, Status status) {
        List<ItemRequest> items = itemRepository.findAllByTeamIdAndStatus(teamId, status);
        return items.stream()
                .map(ItemResponseDto::from)
                .toList();
    }

    @Transactional
    public void addDeliveryNumber(Long itemId, String deliveryNumber) {
        ItemRequest item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));

        if (!deliveryNumber.matches("\\d+")) {
            throw new IllegalArgumentException("운송장 번호는 숫자만 허용됩니다.");
        }
        item = ItemRequestFactory.withUpdatedDeliveryNumber(item, Integer.parseInt(deliveryNumber));
        itemRepository.save(item);
    }
  
    public void rejectItem(Long itemId, String reason) {
        ItemRequest item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));

        if (item.getStatus() != Status.PENDING) {
            throw new IllegalStateException("PENDING 상태의 요청만 거절할 수 있습니다.");
        }
        item = ItemRequestFactory.rejectWithReason(item, reason);
        itemRepository.save(item);
    }

    public ApproveItemResponseDto approveItem(Long itemId) {
        ItemRequest item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));

        if (item.getStatus() != Status.PENDING) {
            throw new IllegalStateException("PENDING 상태의 아이템만 승인할 수 있습니다.");
        }

        item.changeStatus(Status.APPROVED);
        itemRepository.save(item);

        return ApproveItemResponseDto.of();
    }

    @Transactional
    public ApproveItemResponseDto approveAllItems() {
        List<ItemRequest> pendingItems = itemRepository.findAllByStatus(Status.PENDING);

        for (ItemRequest item : pendingItems) {
            item.changeStatus(Status.APPROVED);
        }

        itemRepository.saveAll(pendingItems);

        return ApproveItemResponseDto.of();
    }
}
