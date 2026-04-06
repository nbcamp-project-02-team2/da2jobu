package com.delivery.hubpath.application.dto;

import java.util.UUID;

public record UpdateHubPathCommand(
        UUID hub_path_id,
        UUID departHubId,
        UUID arriveHubId
) {
    public static UpdateHubPathCommand of(UUID hub_path_id, UUID departHubId, UUID arriveHubId) {
        return new UpdateHubPathCommand(hub_path_id, departHubId, arriveHubId);
    }
}