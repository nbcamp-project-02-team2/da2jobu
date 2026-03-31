package com.da2jobu.domain.model.vo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class CompanyIdTest {

    @Test
    void of_generatesUniqueIds() {
        assertThat(CompanyId.of()).isNotEqualTo(CompanyId.of());
    }

    @Test
    void of_withNull_throwsException() {
        assertThatThrownBy(() -> CompanyId.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void isSameAs_withSameUUID_returnsTrue() {
        UUID uuid = UUID.randomUUID();
        assertThat(CompanyId.of(uuid).isSameAs(uuid)).isTrue();
    }

    @Test
    void isSameAs_withDifferentUUID_returnsFalse() {
        assertThat(CompanyId.of(UUID.randomUUID()).isSameAs(UUID.randomUUID())).isFalse();
    }
}
