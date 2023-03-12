package com.baeldung.instancio.settings;

import org.instancio.Instancio;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UsingInstancioPropertiesUnitTest {

    /**
     * Instancio automatically loads {@code instancio.properties} file,
     * if it's present, from the root of the classpath.
     *
     * <p>Float range was overridden to [0, 100].
     * See: {@code src/test/resources/instancio.properties}
     */
    @Test
    void whenInstancioPropertiesAreOnClasspath_shouldUseConfiguredProperties() {
        Set<Float> floats = Instancio.ofSet(Float.class).create();

        assertThat(floats)
                .isNotEmpty()
                .allSatisfy(f -> assertThat(f).isBetween(0f, 100f));
    }

    /**
     * We can override global configuration using {@link Settings}.
     */
    @Test
    void whenCustomSettingsAreProvided_shouldOverrideInstancioProperties() {
        // Given
        Settings settings = Settings.create()
                .set(Keys.FLOAT_MIN, 100f)
                .set(Keys.FLOAT_MAX, 200f);

        // When
        Set<Float> floats = Instancio.ofSet(Float.class)
                .withSettings(settings)
                .create();

        // Then
        assertThat(floats)
                .isNotEmpty()
                .allSatisfy(f -> assertThat(f).isBetween(100f, 200f));
    }
}
