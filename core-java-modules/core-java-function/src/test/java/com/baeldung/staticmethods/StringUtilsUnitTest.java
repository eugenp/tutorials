package com.baeldung.staticmethods;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsUnitTest {

    @Test
    void givenSimpleString_whenCapitalizeStaticMethodIsCalled_thenCapitalizedStringIsReturned() {
        String str = StringUtils.capitalize("baeldung");
        assertThat(str).isEqualTo("Baeldung");
    }

}
