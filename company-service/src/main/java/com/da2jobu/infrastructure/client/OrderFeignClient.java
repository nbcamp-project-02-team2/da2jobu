package com.da2jobu.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "order-service")
public interface OrderFeignClient {

    @GetMapping("/api/orders/{companyId}")
    boolean hasActiveOrders(@PathVariable("companyId") UUID orderId);
}