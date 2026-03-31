package com.da2jobu.domain.model.entity;

import com.da2jobu.domain.model.vo.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class CompanyTest {

    private static final UUID HUB_UUID = UUID.randomUUID();
    private static final String ADDRESS = "Seoul Gangnam-gu Teheran-ro 1";

    @Test
    void create_setsAllFieldsCorrectly() {
        Company company = company();

        assertThat(company.getName()).isEqualTo("Test Company");
        assertThat(company.getType()).isEqualTo(CompanyType.RECEIVER);
        assertThat(company.getHubId().isSameAs(HUB_UUID)).isTrue();
        assertThat(company.getLocation().getAddress()).isEqualTo(ADDRESS);
    }

    @Test
    void update_changesAllFields() {
        Company company = company();
        UUID newHubId = UUID.randomUUID();

        company.update("Updated", CompanyType.PRODUCER, HubId.of(newHubId), location("New Address"));

        assertThat(company.getName()).isEqualTo("Updated");
        assertThat(company.getType()).isEqualTo(CompanyType.PRODUCER);
        assertThat(company.getHubId().isSameAs(newHubId)).isTrue();
        assertThat(company.getLocation().getAddress()).isEqualTo("New Address");
    }

    @Test
    void isHubChanged_returnsTrueForDifferentHub() {
        assertThat(company().isHubChanged(UUID.randomUUID())).isTrue();
    }

    @Test
    void isHubChanged_returnsFalseForSameHub() {
        assertThat(company().isHubChanged(HUB_UUID)).isFalse();
    }

    @Test
    void isAddressChanged_returnsTrueForDifferentAddress() {
        assertThat(company().isAddressChanged("Other Address")).isTrue();
    }

    @Test
    void isAddressChanged_returnsFalseForSameAddress() {
        assertThat(company().isAddressChanged(ADDRESS)).isFalse();
    }

    @Test
    void belongsToHub_returnsTrueForMatchingHub() {
        assertThat(company().belongsToHub(HubId.of(HUB_UUID))).isTrue();
    }

    @Test
    void belongsToHub_returnsFalseForDifferentHub() {
        assertThat(company().belongsToHub(HubId.of(UUID.randomUUID()))).isFalse();
    }

    private Company company() {
        return Company.create(CompanyId.of(), HubId.of(HUB_UUID), "Test Company", CompanyType.RECEIVER, location(ADDRESS));
    }

    private Location location(String address) {
        return Location.of(address, new BigDecimal("37.5"), new BigDecimal("127.0"));
    }
}
