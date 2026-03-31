package com.da2joburereung.userservice.user.presentation;

import com.da2joburereung.userservice.user.application.UserApplicationService;
import com.da2joburereung.userservice.user.dto.response.UserResponse;
import common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserApplicationService userApplicationService;

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse<UserResponse>> getUser(@PathVariable UUID userId) {
        UserResponse response = userApplicationService.getUser(userId);
        return CommonResponse.ok("사용자 조회에 성공했습니다.", response);
    }

    @GetMapping("/me")
    public ResponseEntity<CommonResponse<UserResponse>> getMyInfo(
            @RequestHeader("X-User-Id") String userId
    ) {
        UserResponse response = userApplicationService.getMyInfo(userId);
        return CommonResponse.ok("내 정보 조회에 성공했습니다.", response);
    }

    @PatchMapping("/{userId}/approvals")
    public ResponseEntity<CommonResponse<?>> approveUser(@PathVariable UUID userId) {
        userApplicationService.approveUser(userId);
        return CommonResponse.ok("사용자 승인이 완료되었습니다.");
    }

    @PatchMapping("/{userId}/rejection")
    public ResponseEntity<CommonResponse<?>> rejectUser(@PathVariable UUID userId) {
        userApplicationService.rejectUser(userId);
        return CommonResponse.ok("사용자 거절이 완료되었습니다.");
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse<?>> deleteUser(@PathVariable UUID userId) {
        userApplicationService.deleteUser(userId, "system");
        return CommonResponse.ok("사용자 삭제가 완료되었습니다.");
    }
}