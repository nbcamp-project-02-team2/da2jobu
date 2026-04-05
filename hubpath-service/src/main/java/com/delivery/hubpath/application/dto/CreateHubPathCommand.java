package com.delivery.hubpath.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "허브 경로 생성 커맨드")
public record CreateHubPathCommand (
        UUID departHubId,
        UUID arriveHubId
)
{
    public static CreateHubPathCommand of(UUID departHubId, UUID arriveHubId) {
        return new CreateHubPathCommand(departHubId, arriveHubId);
    }
}