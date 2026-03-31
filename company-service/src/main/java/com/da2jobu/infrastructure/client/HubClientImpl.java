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
//    @Retry(name = "hubService")
//    @CircuitBreaker(name = "hubService", fallbackMethod = "hubServiceFallback")
    public void validateHubExists(UUID hubId) {
        // TODO: Postman 테스트용 stub — hub-service 연동 시 아래 주석 해제
//        try {
//            hubFeignClient.getHub(hubId);
//        } catch (FeignException.NotFound e) {
//            throw new CustomException(ErrorCode.HUB_NOT_FOUND);
//        }
        log.info("[STUB] 허브 존재 검증 스킵: hubId={}", hubId);
    }

    private void hubServiceFallback(UUID hubId, Throwable t) {
        log.error("CircuitBreaker 허브 서비스 호출 불가: hubId={}, cause={}", hubId, t.getMessage());
        throw new CustomException(ErrorCode.HUB_SERVICE_ERROR);
    }
}