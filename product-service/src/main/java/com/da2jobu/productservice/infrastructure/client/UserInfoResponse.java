package com.da2jobu.productservice.infrastructure.client;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserInfoResponse {
    private UUID hubId;
    private UUID companyId;
}
