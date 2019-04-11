package com.baeldung.stringisnumeric;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsIsNumericSpaceUnitTest {
    @Test
    public void givenApacheCommons_whenUsingIsNumericSpace_thenTrue() {
        // Valid Numbers
        assertThat(StringUtils.isNumericSpace("123")).isTrue();
        assertThat(StringUtils.isNumericSpace("١٢٣")).isTrue();
        assertThat(StringUtils.isNumericSpace("")).isTrue();
        assertThat(StringUtils.isNumericSpace("  ")).isTrue();
        assertThat(StringUtils.isNumericSpace("12 3")).isTrue();
         
        // Invalid Numbers
        assertThat(StringUtils.isNumericSpace(null)).isFalse();
        assertThat(StringUtils.isNumericSpace("ab2c")).isFalse();
        assertThat(StringUtils.isNumericSpace("12.3")).isFalse();
        assertThat(StringUtils.isNumericSpace("-123")).isFalse();
    }
}
