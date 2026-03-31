package com.da2jobu.domain.service;

import com.da2jobu.domain.model.entity.Company;
import com.da2jobu.domain.model.vo.*;
import common.exception.CustomException;
import common.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class CompanyDomainServiceTest {

    private CompanyDomainService domainService;
    private UUID hubId;
    private UUID companyId;
    private Company company;

    @BeforeEach
    void setUp() {
        domainService = new CompanyDomainService();
        hubId = UUID.randomUUID();
        companyId = UUID.randomUUID();
        company = Company.create(
                CompanyId.of(companyId), HubId.of(hubId), "Test",
                CompanyType.RECEIVER,
                Location.of("Seoul", new BigDecimal("37.5"), new BigDecimal("127.0"))
        );
    }

    // ── validateCreateAccess ──────────────────────────────

    @Test
    void createAccess_masterAlwaysPasses() {
        assertThatNoException().isThrownBy(
                () -> domainService.validateCreateAccess("MASTER", null, UUID.randomUUID()));
    }

    @Test
    void createAccess_hubManagerWithOwnHub_passes() {
        assertThatNoException().isThrownBy(
                () -> domainService.validateCreateAccess("HUB_MANAGER", hubId, hubId));
    }

    @Test
    void createAccess_hubManagerWithDifferentHub_throwsHubMismatch() {
        assertThatThrownBy(() -> domainService.validateCreateAccess("HUB_MANAGER", hubId, UUID.randomUUID()))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_HUB_MISMATCH);
    }

    @Test
    void createAccess_hubManagerWithNullUserHubId_throwsHubMismatch() {
        assertThatThrownBy(() -> domainService.validateCreateAccess("HUB_MANAGER", null, hubId))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_HUB_MISMATCH);
    }

    @Test
    void createAccess_otherRole_throwsCreateForbidden() {
        assertThatThrownBy(() -> domainService.validateCreateAccess("DELIVERY_MANAGER", null, hubId))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_CREATE_FORBIDDEN);
    }

    // ── validateUpdateAccess ──────────────────────────────

    @Test
    void updateAccess_masterAlwaysPasses() {
        assertThatNoException().isThrownBy(
                () -> domainService.validateUpdateAccess(company, "MASTER", null, null));
    }

    @Test
    void updateAccess_hubManagerWithOwnHub_passes() {
        assertThatNoException().isThrownBy(
                () -> domainService.validateUpdateAccess(company, "HUB_MANAGER", hubId, null));
    }

    @Test
    void updateAccess_hubManagerWithDifferentHub_throwsHubMismatch() {
        assertThatThrownBy(() -> domainService.validateUpdateAccess(company, "HUB_MANAGER", UUID.randomUUID(), null))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_HUB_MISMATCH);
    }

    @Test
    void updateAccess_companyManagerWithOwnCompany_passes() {
        assertThatNoException().isThrownBy(
                () -> domainService.validateUpdateAccess(company, "COMPANY_MANAGER", null, companyId));
    }

    @Test
    void updateAccess_companyManagerWithDifferentCompany_throwsUpdateForbidden() {
        assertThatThrownBy(() -> domainService.validateUpdateAccess(company, "COMPANY_MANAGER", null, UUID.randomUUID()))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_UPDATE_FORBIDDEN);
    }

    @Test
    void updateAccess_otherRole_throwsUpdateForbidden() {
        assertThatThrownBy(() -> domainService.validateUpdateAccess(company, "DELIVERY_MANAGER", null, null))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_UPDATE_FORBIDDEN);
    }

    // ── validateDeleteAccess ──────────────────────────────

    @Test
    void deleteAccess_masterAlwaysPasses() {
        assertThatNoException().isThrownBy(
                () -> domainService.validateDeleteAccess(company, "MASTER", null));
    }

    @Test
    void deleteAccess_hubManagerWithOwnHub_passes() {
        assertThatNoException().isThrownBy(
                () -> domainService.validateDeleteAccess(company, "HUB_MANAGER", hubId));
    }

    @Test
    void deleteAccess_hubManagerWithDifferentHub_throwsHubMismatch() {
        assertThatThrownBy(() -> domainService.validateDeleteAccess(company, "HUB_MANAGER", UUID.randomUUID()))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_HUB_MISMATCH);
    }

    @Test
    void deleteAccess_otherRole_throwsDeleteForbidden() {
        assertThatThrownBy(() -> domainService.validateDeleteAccess(company, "COMPANY_MANAGER", null))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_DELETE_FORBIDDEN);
    }
}
