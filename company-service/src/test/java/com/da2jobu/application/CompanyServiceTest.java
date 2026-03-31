package com.da2jobu.application;

import com.da2jobu.application.dto.command.CreateCompanyCommand;
import com.da2jobu.application.dto.command.UpdateCompanyCommand;
import com.da2jobu.application.dto.query.SearchCompanyQuery;
import com.da2jobu.application.dto.result.CompanyResult;
import com.da2jobu.application.service.CompanyEventPublisher;
import com.da2jobu.application.service.HubClient;
import com.da2jobu.application.service.LocationClient;
import com.da2jobu.application.service.OrderClient;
import com.da2jobu.domain.model.entity.Company;
import com.da2jobu.domain.model.vo.*;
import com.da2jobu.domain.repository.CompanyRepository;
import com.da2jobu.domain.service.CompanyDomainService;
import common.exception.CustomException;
import common.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @InjectMocks private CompanyService companyService;

    @Mock private LocationClient locationClient;
    @Mock private HubClient hubClient;
    @Mock private OrderClient orderClient;
    @Mock private CompanyEventPublisher companyEventPublisher;
    @Mock private CompanyRepository companyRepository;
    @Mock private CompanyDomainService companyDomainService;

    private UUID companyId;
    private UUID hubId;
    private Company company;

    @BeforeEach
    void setUp() {
        companyId = UUID.randomUUID();
        hubId = UUID.randomUUID();
        company = Company.create(
                CompanyId.of(companyId), HubId.of(hubId), "Test Company",
                CompanyType.RECEIVER,
                Location.of("Seoul Gangnam-gu", new BigDecimal("37.5"), new BigDecimal("127.0"))
        );
    }

    // ── createCompany ─────────────────────────────────────

    @Test
    void createCompany_savesAndReturnsResult() {
        CreateCompanyCommand command = new CreateCompanyCommand("MASTER", null, hubId, "New Co", CompanyType.RECEIVER, "Seoul");
        given(locationClient.resolveLocation(any())).willReturn(company.getLocation());
        given(companyRepository.save(any())).willReturn(company);

        CompanyResult result = companyService.createCompany(command);

        assertThat(result).isNotNull();
        then(hubClient).should().validateHubExists(hubId);
        then(companyRepository).should().save(any(Company.class));
    }

    @Test
    void createCompany_whenDomainServiceThrows_propagatesException() {
        CreateCompanyCommand command = new CreateCompanyCommand("DELIVERY_MANAGER", null, hubId, "New Co", CompanyType.RECEIVER, "Seoul");
        willThrow(new CustomException(ErrorCode.COMPANY_CREATE_FORBIDDEN))
                .given(companyDomainService).validateCreateAccess(any(), any(), any());

        assertThatThrownBy(() -> companyService.createCompany(command))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_CREATE_FORBIDDEN);

        then(companyRepository).shouldHaveNoInteractions();
    }

    // ── getCompany ────────────────────────────────────────

    @Test
    void getCompany_returnsResult() {
        given(companyRepository.findByIdAndDeletedAtIsNull(companyId)).willReturn(Optional.of(company));

        CompanyResult result = companyService.getCompany(companyId);

        assertThat(result.companyId()).isEqualTo(companyId);
    }

    @Test
    void getCompany_notFound_throwsCompanyNotFound() {
        given(companyRepository.findByIdAndDeletedAtIsNull(companyId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> companyService.getCompany(companyId))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_NOT_FOUND);
    }

    // ── searchCompanies ───────────────────────────────────

    @Test
    void searchCompanies_invalidSize_defaultsTo10() {
        given(companyRepository.search(isNull(), isNull(), isNull(), eq(PageRequest.of(0, 10))))
                .willReturn(new PageImpl<>(List.of(company)));

        companyService.searchCompanies(new SearchCompanyQuery(null, null, null, 0, 99));

        then(companyRepository).should().search(null, null, null, PageRequest.of(0, 10));
    }

    // ── update ────────────────────────────────────────────

    @Test
    void update_sameHubAndAddress_skipsHubValidationAndLocationResolve() {
        UpdateCompanyCommand command = new UpdateCompanyCommand(
                companyId, "MASTER", null, null, hubId, "Updated", CompanyType.PRODUCER, "Seoul Gangnam-gu");
        given(companyRepository.findByIdAndDeletedAtIsNull(companyId)).willReturn(Optional.of(company));
        given(companyRepository.save(any())).willReturn(company);

        companyService.update(command);

        then(hubClient).shouldHaveNoInteractions();
        then(locationClient).shouldHaveNoInteractions();
    }

    @Test
    void update_hubChanged_validatesNewHub() {
        UUID newHubId = UUID.randomUUID();
        UpdateCompanyCommand command = new UpdateCompanyCommand(
                companyId, "MASTER", null, null, newHubId, "Updated", CompanyType.PRODUCER, "Seoul Gangnam-gu");
        given(companyRepository.findByIdAndDeletedAtIsNull(companyId)).willReturn(Optional.of(company));
        given(companyRepository.save(any())).willReturn(company);

        companyService.update(command);

        then(hubClient).should().validateHubExists(newHubId);
    }

    @Test
    void update_addressChanged_resolvesNewLocation() {
        String newAddress = "Seoul Seocho-gu";
        UpdateCompanyCommand command = new UpdateCompanyCommand(
                companyId, "MASTER", null, null, hubId, "Updated", CompanyType.PRODUCER, newAddress);
        given(companyRepository.findByIdAndDeletedAtIsNull(companyId)).willReturn(Optional.of(company));
        given(locationClient.resolveLocation(newAddress)).willReturn(
                Location.of(newAddress, new BigDecimal("37.4"), new BigDecimal("127.1")));
        given(companyRepository.save(any())).willReturn(company);

        companyService.update(command);

        then(locationClient).should().resolveLocation(newAddress);
    }

    // ── deleteCompany ─────────────────────────────────────

    @Test
    void deleteCompany_noActiveOrders_deletesAndPublishesEvent() {
        given(companyRepository.findByIdAndDeletedAtIsNull(companyId)).willReturn(Optional.of(company));
        given(orderClient.hasActiveOrders(companyId)).willReturn(false);

        companyService.deleteCompany(companyId, "MASTER", null, "admin");

        then(companyEventPublisher).should().publishCompanyDeleted(eq(companyId), eq("admin"), any());
    }

    @Test
    void deleteCompany_hasActiveOrders_throwsAndNoEvent() {
        given(companyRepository.findByIdAndDeletedAtIsNull(companyId)).willReturn(Optional.of(company));
        given(orderClient.hasActiveOrders(companyId)).willReturn(true);

        assertThatThrownBy(() -> companyService.deleteCompany(companyId, "MASTER", null, "admin"))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_HAS_ACTIVE_ORDERS);

        then(companyEventPublisher).shouldHaveNoInteractions();
    }

    @Test
    void deleteCompany_notFound_throwsCompanyNotFound() {
        given(companyRepository.findByIdAndDeletedAtIsNull(companyId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> companyService.deleteCompany(companyId, "MASTER", null, "admin"))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_NOT_FOUND);
    }
}
