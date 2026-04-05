package com.delivery.hubpath.infrastructure.client;

import common.dto.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "HUB-SERVICE")
public interface HubClient {

    @GetMapping("/api/hubs")
    CommonResponse getHubs(
            @RequestParam(value = "hub_id", required = false) UUID hubId,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "page") int page
    );

    @GetMapping("/api/hubs/all")
    CommonResponse<List<HubResponse>> getAllHubs();
}