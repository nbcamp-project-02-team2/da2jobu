package com.da2jobu.domain.model.vo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class LocationTest {

    private static final String ADDRESS = "Seoul Gangnam-gu Teheran-ro 1";
    private static final BigDecimal LAT = new BigDecimal("37.5");
    private static final BigDecimal LON = new BigDecimal("127.0");

    @Test
    void of_withValidValues_createsLocation() {
        Location location = Location.of(ADDRESS, LAT, LON);

        assertThat(location.getAddress()).isEqualTo(ADDRESS);
        assertThat(location.getLatitude()).isEqualByComparingTo(LAT);
        assertThat(location.getLongitude()).isEqualByComparingTo(LON);
    }

    @Test
    void of_withBlankAddress_throwsException() {
        assertThatThrownBy(() -> Location.of("  ", LAT, LON))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({"91, 127.0", "-91, 127.0"})
    void of_withLatitudeOutOfRange_throwsException(String lat, String lon) {
        assertThatThrownBy(() -> Location.of(ADDRESS, new BigDecimal(lat), new BigDecimal(lon)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({"37.5, 181", "37.5, -181"})
    void of_withLongitudeOutOfRange_throwsException(String lat, String lon) {
        assertThatThrownBy(() -> Location.of(ADDRESS, new BigDecimal(lat), new BigDecimal(lon)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void isSameAddress_withSameAddress_returnsTrue() {
        assertThat(Location.of(ADDRESS, LAT, LON).isSameAddress(ADDRESS)).isTrue();
    }

    @Test
    void isSameAddress_withDifferentAddress_returnsFalse() {
        assertThat(Location.of(ADDRESS, LAT, LON).isSameAddress("Seoul Seocho-gu")).isFalse();
    }
}
