package com.da2jobu.domain.model.vo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class HubIdTest {

    @Test
    void of_withNull_throwsException() {
        assertThatThrownBy(() -> HubId.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void isSameAs_withSameUUID_returnsTrue() {
        UUID uuid = UUID.randomUUID();
        assertThat(HubId.of(uuid).isSameAs(uuid)).isTrue();
    }

    @Test
    void isSameAs_withDifferentUUID_returnsFalse() {
        assertThat(HubId.of(UUID.randomUUID()).isSameAs(UUID.randomUUID())).isFalse();
    }

    @Test
    void equals_withSameUUID_returnsTrue() {
        UUID uuid = UUID.randomUUID();
        assertThat(HubId.of(uuid)).isEqualTo(HubId.of(uuid));
    }
}
