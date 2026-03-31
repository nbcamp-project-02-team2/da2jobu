package com.da2jobu.application.dto.query;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class SearchCompanyQueryTest {

    @ParameterizedTest
    @ValueSource(ints = {10, 30, 50})
    void validatedSize_allowedSize_returnsAsIs(int size) {
        assertThat(new SearchCompanyQuery(null, null, null, 0, size).validatedSize()).isEqualTo(size);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 20, 100, -1})
    void validatedSize_disallowedSize_returnsDefault10(int size) {
        assertThat(new SearchCompanyQuery(null, null, null, 0, size).validatedSize()).isEqualTo(10);
    }
}
