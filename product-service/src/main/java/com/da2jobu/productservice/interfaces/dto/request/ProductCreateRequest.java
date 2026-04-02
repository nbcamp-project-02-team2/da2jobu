package com.da2jobu.productservice.interfaces.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 상품 생성 요청 DTO.
 * - @Valid 를 통해 필수값 및 형식 검증 수행
 */
@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotBlank(message = "상품명은 필수입니다.")
    @Size(max = 255, message = "상품명은 255자 이내여야 합니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @DecimalMin(value = "0.00", inclusive = false, message = "가격은 0보다 커야 합니다.")
    @Digits(integer = 13, fraction = 2, message = "가격 형식이 올바르지 않습니다.")
    private BigDecimal price;

    @NotNull(message = "재고 수량은 필수입니다.")
    @Min(value = 0, message = "재고 수량은 0 이상이어야 합니다.")
    private Integer stockQuantity;

    @NotNull(message = "허브 ID는 필수입니다.")
    private UUID hubId;

    @NotNull(message = "업체 ID는 필수입니다.")
    private UUID companyId;

    private String description;
}
