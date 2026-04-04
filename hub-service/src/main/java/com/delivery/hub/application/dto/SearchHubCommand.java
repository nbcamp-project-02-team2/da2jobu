package com.delivery.hub.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "허브 검색 커맨드")
public record SearchHubCommand(
        UUID hub_id,
        String hub_name,
        String address
){
    public static SearchHubCommand of(UUID hub_id,String hub_name, String address) {
        return new SearchHubCommand(hub_id,hub_name,address);
    }
}
