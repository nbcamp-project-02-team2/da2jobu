package com.da2jobu.infrastructure.client;

import com.da2jobu.application.client.UserClient;
import com.da2jobu.infrastructure.client.dto.UserResponse;
import common.dto.CommonResponse;
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
public class UserClientImpl implements UserClient {

    private final UserFeignClient userFeignClient;

    @Override
    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "userServiceFallback")
    public UserInfo getUserInfo(UUID userId) {
        try {
            /**
             * todo : 유저 내부 api 요청 (변경예정)
             */
            CommonResponse<UserResponse> response = userFeignClient.getUserInfo(userId, "MASTER");
            UserResponse userResponse = response.getData();
            return new UserInfo(userResponse.hubId(), userResponse.companyId());
        } catch (FeignException.NotFound e) {
            return null;
        }
    }

    private UserInfo userServiceFallback(UUID userId, Throwable t) {
        log.error("CircuitBreaker 유저 서비스 호출 불가: userId={}, cause={}", userId, t.getMessage());
        throw new CustomException(ErrorCode.USER_SERVICE_ERROR);
    }
}