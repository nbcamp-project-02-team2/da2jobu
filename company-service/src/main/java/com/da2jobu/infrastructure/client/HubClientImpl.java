package com.da2jobu.infrastructure.client;

import com.da2jobu.application.service.HubClient;
import common.exception.CustomException;
import common.exception.ErrorCode;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubClientImpl implements HubClient {

    private final HubFeignClient hubFeignClient;

    @Override
    @Retry(name = "hubService")
    @CircuitBreaker(name = "hubService", fallbackMethod = "hubServiceFallback")
    public void validateHubExists(UUID hubId) {
        try {
            /**
             * todo : 허브 매니저는 본인 담당 허브(hub_id)인지 허브 id가 존재하는지 검증
             */
            hubFeignClient.getHub(hubId);
        } catch (FeignException.NotFound e) {
            // 404는 재시도 의미 없음 → 즉시 예외 변환
            throw new CustomException(ErrorCode.HUB_NOT_FOUND);
        }
        // 그 외 FeignException은 Retry가 재시도, 소진 시 CB fallback
    }

    private void hubServiceFallback(UUID hubId, Throwable t) {
        log.error("CircuitBreaker 허브 서비스 호출 불가: hubId={}, cause={}", hubId, t.getMessage());
        throw new CustomException(ErrorCode.HUB_SERVICE_ERROR);
    }
}
