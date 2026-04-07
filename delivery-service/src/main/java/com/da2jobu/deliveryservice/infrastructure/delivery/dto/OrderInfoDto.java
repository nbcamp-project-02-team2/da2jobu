package com.da2jobu.deliveryservice.infrastructure.delivery.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderInfoDto(
        UUID id,
        UUID productId,
        Integer quantity,
        BigDecimal unitPrice,
        String status,
        UUID supplierId,
        UUID receiverId,
        UUID deliveryId,
        UUID hubId,
        String requirements,
        LocalDateTime desiredDeliveryAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}