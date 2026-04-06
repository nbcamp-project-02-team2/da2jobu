package com.delivery.hub.application.dto;

import java.util.UUID;

public record UpdateHubCommand(
        UUID hubId,
        String hub_name,
        String address
) {
    public static UpdateHubCommand of(UUID hubId, String hub_name, String address) {
        return new UpdateHubCommand(hubId, hub_name, address);
    }
}