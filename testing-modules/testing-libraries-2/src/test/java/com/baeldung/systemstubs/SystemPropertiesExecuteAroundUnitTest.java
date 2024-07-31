package com.baeldung.systemstubs;

import org.junit.jupiter.api.Test;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.org.webcompere.systemstubs.SystemStubs.restoreSystemProperties;

class SystemPropertiesExecuteAroundUnitTest {
    @Test
    void givenRestoreSystemProperties_thenPropertyRestored() throws Exception {
        restoreSystemProperties(() -> {
            // test code
            System.setProperty("unrestored", "true");
        });

        assertThat(System.getProperty("unrestored")).isNull();
    }

    @Test
    void givenSystemPropertiesObject_thenPropertyRestored() throws Exception {
        String result = new SystemProperties()
          .execute(() -> {
                System.setProperty("unrestored", "true");
                return "it works";
            });

        assertThat(result).isEqualTo("it works");
        assertThat(System.getProperty("unrestored")).isNull();
    }
}
