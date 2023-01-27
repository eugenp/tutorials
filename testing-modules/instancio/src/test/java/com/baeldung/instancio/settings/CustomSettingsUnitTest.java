package com.baeldung.instancio.settings;

import com.baeldung.instancio.student.model.ContactInfo;
import com.baeldung.instancio.student.model.Phone;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.WithSettings;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link InstancioExtension} allows injecting custom settings
 * using the {@link WithSettings} annotation.
 */
@ExtendWith(InstancioExtension.class)
class CustomSettingsUnitTest {

    private static final int MIN_SIZE = 0;
    private static final int MAX_SIZE = 3;

    /**
     * Common settings to be used by all test methods.
     */
    @WithSettings
    private static final Settings settings = Settings.create()
            .set(Keys.COLLECTION_MIN_SIZE, MIN_SIZE)
            .set(Keys.COLLECTION_MAX_SIZE, MAX_SIZE)
            .lock();


    @Test
    void whenGivenInjectedSettings_shouldUseCustomSettings() {
        ContactInfo info = Instancio.create(ContactInfo.class);

        List<Phone> phones = info.getPhones();
        assertThat(phones).hasSizeBetween(MIN_SIZE, MAX_SIZE);
    }

    @Test
    void whenSettingsOverridden_shouldUseTheOverrides() {
        // Given
        final int collectionSize = 50;
        Settings additionalSettings = Settings.create()
                .set(Keys.STRING_FIELD_PREFIX_ENABLED, true)
                .set(Keys.COLLECTION_MIN_SIZE, collectionSize)
                .set(Keys.COLLECTION_MAX_SIZE, collectionSize);

        // When
        ContactInfo info = Instancio.of(ContactInfo.class)
                .withSettings(additionalSettings)
                .create();

        // Then
        assertThat(info.getPhones())
                .hasSize(collectionSize)
                .allSatisfy(phone -> {
                    assertThat(phone.getCountryCode()).startsWith("countryCode_");
                    assertThat(phone.getNumber()).startsWith("number_");
                });
    }
}
