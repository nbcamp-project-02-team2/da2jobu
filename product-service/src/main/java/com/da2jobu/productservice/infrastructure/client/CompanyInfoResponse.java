package com.da2jobu.productservice.infrastructure.client;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class CompanyInfoResponse {
    private UUID companyId;
    private UUID hubId;
}
