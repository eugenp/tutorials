package com.baeldung.version;

import org.apache.commons.lang3.SystemUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class VersionUnitTest {

    @Test
    public void givenJava_whenUsingRuntime_thenGetVersion() {
        String expectedVersion = "11";
        Runtime.Version runtimeVersion = Runtime.version();
        String version = String.valueOf(runtimeVersion.version().get(0));
        Assertions.assertThat(version).isEqualTo(expectedVersion);
    }

    @Test
    @Disabled("Only valid for Java 8 and lower")
    public void givenJava_whenUsingCommonsLang_thenGetVersion() {
        String expectedVersion = "8";
        String version = SystemUtils.JAVA_SPECIFICATION_VERSION;
        if (version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int separator = version.indexOf(".");
            if (separator != -1) {
                version = version.substring(0, separator);
            }
        }
        Assertions.assertThat(version).isEqualTo(expectedVersion);
    }

    @Test
    @Disabled("Only valid for Java 8 and lower")
    public void givenJava_whenUsingSystemProp_thenGetVersion() {
        String expectedVersion = "8";
        String version = System.getProperty("java.version");
        if (version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int separator = version.indexOf(".");
            if (separator != -1) {
                version = version.substring(0, separator);
            }
        }
        Assertions.assertThat(version).isEqualTo(expectedVersion);
    }
}

