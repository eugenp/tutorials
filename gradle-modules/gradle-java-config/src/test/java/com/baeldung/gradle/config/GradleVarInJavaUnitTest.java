package com.baeldung.gradle.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.Test;

class GradleVarInJavaUnitTest {

    @Test
    void whenUsingGenerateBuildConfigTask_thenBuildConfigIsGeneratedWithCorrectValue() {
        assertEquals("1.2.3", BuildConfig.MY_VERSION);
    }

    @Test
    void whenUsingGenerateProperties_thenPropertiesFileIsGeneratedWithCorrectValue() throws IOException {
        Properties props = new Properties();
        props.load(Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("version.properties"));
        String version = props.getProperty("MY_VERSION");
        assertEquals("1.2.3", version);
    }

    @Test
    void whenUsingJvmArg_thenValueIsReadCorrectly() {
        assertEquals("1.2.3", System.getProperty("MY_VERSION"));
    }

    @Test
    void whenUsingEnvArg_thenValueIsReadCorrectly() {
        assertEquals("1.2.3", System.getenv("MY_VERSION"));
    }

}
