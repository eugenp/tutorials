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
        int expectedVersion = 8;
        String[] versionElements = SystemUtils.JAVA_SPECIFICATION_VERSION.split("\\.");
        int discard = Integer.parseInt(versionElements[0]);
        int version;
        if (discard == 1) {
            version = Integer.parseInt(versionElements[1]);
        } else {
            version = discard;
        }
        Assertions.assertThat(version).isEqualTo(expectedVersion);
    }

    @Test
    @Disabled("Only valid for Java 8 and lower")
    public void givenJava_whenUsingSystemProp_thenGetVersion() {
        int expectedVersion = 8;
        String[] versionElements = System.getProperty("java.version").split("\\.");
        int discard = Integer.parseInt(versionElements[0]);
        int version;
        if (discard == 1) {
            version = Integer.parseInt(versionElements[1]);
        } else {
            version = discard;
        }
        Assertions.assertThat(version).isEqualTo(expectedVersion);
    }
}

