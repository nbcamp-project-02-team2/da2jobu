package com.da2jobu.infrastructure.client;

import com.da2jobu.infrastructure.client.dto.UserResponse;
import common.dto.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    /**
     * todo: 유저쪽 인터널 api 추가 요청 (변경예정)
     */
    @GetMapping("/api/users/{userId}")
    CommonResponse<UserResponse> getUserInfo(
            @PathVariable("userId") UUID userId,
            @RequestHeader("X-User-Role") String role
    );
}