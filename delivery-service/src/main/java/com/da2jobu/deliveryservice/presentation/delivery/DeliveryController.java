package com.da2jobu.deliveryservice.presentation.delivery;

import com.da2jobu.deliveryservice.application.delivery.dto.CreateDeliveryResponseDto;
import com.da2jobu.deliveryservice.application.delivery.dto.DeliveryDetailResponseDto;
import com.da2jobu.deliveryservice.application.delivery.dto.DeliveryListResponseDto;
import com.da2jobu.deliveryservice.application.delivery.service.DeliveryService;
import com.da2jobu.deliveryservice.domain.delivery.vo.DeliveryStatus;
import com.da2jobu.deliveryservice.presentation.delivery.dto.request.CreateDeliveryRequest;
import com.da2jobu.deliveryservice.presentation.delivery.dto.request.UpdateDeliveryStatusRequest;
import com.da2jobu.deliveryservice.presentation.interceptor.InternalOnly;
import com.da2jobu.deliveryservice.presentation.interceptor.RequireRoles;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeliveryController {

    private final DeliveryService deliveryService;

    // 내부 시스템 전용 (order-service 등) - 일반 사용자 호출 불가
    @PostMapping("/internal/deliveries")
    @InternalOnly
    public CreateDeliveryResponseDto createDelivery(@Valid @RequestBody CreateDeliveryRequest request) {
        return deliveryService.createDelivery(request.toCommand());
    }

    @GetMapping("/deliveries/{deliveryId}")
    @RequireRoles({"MASTER", "HUB_MANAGER", "DELIVERY_MANAGER", "COMPANY_MANAGER"})
    public DeliveryDetailResponseDto getDelivery(
            @PathVariable UUID deliveryId,
            @RequestHeader("X-User-Id") UUID requesterId,
            @RequestHeader("X-User-Role") String requesterRole
    ) {
        return deliveryService.getDelivery(deliveryId, requesterId, requesterRole);
    }

    @GetMapping("/deliveries")
    @RequireRoles({"MASTER", "HUB_MANAGER", "DELIVERY_MANAGER", "COMPANY_MANAGER"})
    public DeliveryListResponseDto getDeliveries(
            @RequestParam(required = false) UUID orderId,
            @RequestParam(required = false) DeliveryStatus status,
            @RequestParam(required = false) UUID originHubId,
            @RequestParam(required = false) UUID destinationHubId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("X-User-Id") UUID requesterId,
            @RequestHeader("X-User-Role") String requesterRole
    ) {
        return deliveryService.getDeliveries(
                orderId, status, originHubId, destinationHubId,
                page, size, requesterId, requesterRole
        );
    }

    @PutMapping("/deliveries/{deliveryId}/status")
    @RequireRoles({"MASTER", "HUB_MANAGER", "DELIVERY_MANAGER"})
    public DeliveryDetailResponseDto updateDeliveryStatus(
            @PathVariable UUID deliveryId,
            @Valid @RequestBody UpdateDeliveryStatusRequest request,
            @RequestHeader("X-User-Id") UUID requesterId,
            @RequestHeader("X-User-Role") String requesterRole
    ) {
        return deliveryService.updateDeliveryStatus(deliveryId, request.toCommand(), requesterId, requesterRole);
    }

    @DeleteMapping("/deliveries/{deliveryId}")
    @RequireRoles({"MASTER", "HUB_MANAGER"})
    public void deleteDelivery(
            @PathVariable UUID deliveryId,
            @RequestParam String deletedBy,
            @RequestHeader("X-User-Id") UUID requesterId,
            @RequestHeader("X-User-Role") String requesterRole
    ) {
        deliveryService.deleteDelivery(deliveryId, deletedBy, requesterId, requesterRole);
    }
}