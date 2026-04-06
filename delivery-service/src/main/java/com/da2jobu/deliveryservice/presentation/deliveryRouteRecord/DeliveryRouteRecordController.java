package com.da2jobu.deliveryservice.presentation.deliveryRouteRecord;

import com.da2jobu.deliveryservice.application.deliveryRouteRecord.dto.*;
import com.da2jobu.deliveryservice.application.deliveryRouteRecord.service.DeliveryRouteRecordService;
import com.da2jobu.deliveryservice.presentation.deliveryRouteRecord.dto.request.CreateDeliveryRouteRecordsRequest;
import com.da2jobu.deliveryservice.presentation.deliveryRouteRecord.dto.request.UpdateDeliveryRouteMetricsRequest;
import com.da2jobu.deliveryservice.presentation.deliveryRouteRecord.dto.request.UpdateDeliveryRouteStatusRequest;
import com.da2jobu.deliveryservice.presentation.interceptor.InternalOnly;
import com.da2jobu.deliveryservice.presentation.interceptor.RequireRoles;
import common.dto.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeliveryRouteRecordController {

    private final DeliveryRouteRecordService deliveryRouteRecordService;

    // 내부 시스템 전용 (delivery-service 내부 등) - 일반 사용자 호출 불가
    @PostMapping("/internal/delivery-routes")
    @InternalOnly
    public ResponseEntity<CommonResponse<CreateDeliveryRouteRecordsResponseDto>> createDeliveryRouteRecords(
            @Valid @RequestBody CreateDeliveryRouteRecordsRequest request
    ) {
        return CommonResponse.ok(
                deliveryRouteRecordService.createDeliveryRouteRecords(request.toCommand())
        );
    }

    @GetMapping("/delivery-routes/{routeRecordId}")
    @RequireRoles({"MASTER", "HUB_MANAGER", "DELIVERY_MANAGER", "COMPANY_MANAGER"})
    public ResponseEntity<CommonResponse<DeliveryRouteRecordDetailResponseDto>> getDeliveryRouteRecord(
            @PathVariable UUID routeRecordId,
            @RequestHeader("X-User-Id") UUID requesterId,
            @RequestHeader("X-User-Role") String requesterRole
    ) {
        return CommonResponse.ok(
                deliveryRouteRecordService.getDeliveryRouteRecord(routeRecordId, requesterId, requesterRole)
        );
    }

    @GetMapping("/deliveries/{deliveryId}/routes")
    @RequireRoles({"MASTER", "HUB_MANAGER", "DELIVERY_MANAGER", "COMPANY_MANAGER"})
    public ResponseEntity<CommonResponse<DeliveryRouteRecordListResponseDto>> getDeliveryRouteRecords(
            @PathVariable UUID deliveryId,
            @RequestHeader("X-User-Id") UUID requesterId,
            @RequestHeader("X-User-Role") String requesterRole
    ) {
        return CommonResponse.ok(
                deliveryRouteRecordService.getDeliveryRouteRecords(deliveryId, requesterId, requesterRole)
        );
    }

    @PutMapping("/delivery-routes/{routeRecordId}/status")
    @RequireRoles({"MASTER", "HUB_MANAGER", "DELIVERY_MANAGER"})
    public ResponseEntity<CommonResponse<UpdateDeliveryRouteStatusResponseDto>> updateDeliveryRouteStatus(
            @PathVariable UUID routeRecordId,
            @Valid @RequestBody UpdateDeliveryRouteStatusRequest request,
            @RequestHeader("X-User-Id") UUID requesterId,
            @RequestHeader("X-User-Role") String requesterRole
    ) {
        return CommonResponse.ok(
                deliveryRouteRecordService.updateDeliveryRouteStatus(routeRecordId, request.toCommand(),
                        requesterId, requesterRole)
        );
    }

    @PutMapping("/delivery-routes/{routeRecordId}/metrics")
    @RequireRoles({"MASTER", "HUB_MANAGER", "DELIVERY_MANAGER"})
    public ResponseEntity<CommonResponse<UpdateDeliveryRouteMetricsResponseDto>> updateDeliveryRouteMetrics(
            @PathVariable UUID routeRecordId,
            @Valid @RequestBody UpdateDeliveryRouteMetricsRequest request,
            @RequestHeader("X-User-Id") UUID requesterId,
            @RequestHeader("X-User-Role") String requesterRole
    ) {
        return CommonResponse.ok(
                deliveryRouteRecordService.updateDeliveryRouteMetrics(routeRecordId, request.toCommand(),
                        requesterId, requesterRole)
        );
    }

    @DeleteMapping("/delivery-routes/{routeRecordId}")
    @RequireRoles({"MASTER", "HUB_MANAGER"})
    public ResponseEntity<CommonResponse<DeleteDeliveryRouteRecordResponseDto>> deleteDeliveryRouteRecord(
            @PathVariable UUID routeRecordId,
            @RequestParam String deletedBy,
            @RequestHeader("X-User-Id") UUID requesterId,
            @RequestHeader("X-User-Role") String requesterRole
    ) {
        return CommonResponse.ok(
                deliveryRouteRecordService.deleteDeliveryRouteRecord(routeRecordId, deletedBy,
                        requesterId, requesterRole)
        );
    }
}