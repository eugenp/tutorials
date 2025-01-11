package com.baeldung.hibernate.timezonestorage;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
class TimeZoneStorageIntegrationTest {

    @Autowired
    private AstronomicalObservationRepository astronomicalObservationRepository;

    @Test
    void whenNativeStorageStrategyUsed_thenTimestampWithTimezoneSaved() {
        ZonedDateTime observationStartTime = ZonedDateTime.now();
        AstronomicalObservation observation = new AstronomicalObservation();
        observation.setId(UUID.randomUUID());
        observation.setCelestialObjectName("test-planet");
        observation.setObservationStartTime(observationStartTime);

        AstronomicalObservation savedObservation = astronomicalObservationRepository.save(observation);
        assertThat(savedObservation.getObservationStartTime())
            .isEqualTo(observationStartTime);
    }

    @Test
    void whenColumnStorageStrategyUsed_thenTimestampAndOffsetSaved() {
        OffsetDateTime peakVisibilityTime = OffsetDateTime.now();
        AstronomicalObservation observation = new AstronomicalObservation();
        observation.setId(UUID.randomUUID());
        observation.setCelestialObjectName("test-planet");
        observation.setPeakVisibilityTime(peakVisibilityTime);
        AstronomicalObservation savedObservation = astronomicalObservationRepository.save(observation);

        AstronomicalObservation retrievedObservation = astronomicalObservationRepository
            .findById(savedObservation.getId())
            .orElseThrow(IllegalStateException::new);
        assertThat(retrievedObservation)
            .isNotNull()
            .satisfies(obs -> {
                assertThat(obs.getPeakVisibilityTime())
                    .isEqualTo(peakVisibilityTime);
                assertThat(obs.getPeakVisibilityTimeOffset())
                    .isEqualTo(peakVisibilityTime.getOffset().getTotalSeconds());
            });
    }

    @Test
    void whenNormalizeStorageStrategyUsed_thenTimestampNormalizedToLocalTimezoneSaved() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata")); // UTC+05:30
 
        ZonedDateTime nextExpectedAppearence = ZonedDateTime.of(1999, 12, 25, 18, 0, 0, 0, ZoneId.of("UTC+8"));
        AstronomicalObservation observation = new AstronomicalObservation();
        observation.setId(UUID.randomUUID());
        observation.setCelestialObjectName("test-planet");
        observation.setNextExpectedAppearance(nextExpectedAppearence);
        AstronomicalObservation savedObservation = astronomicalObservationRepository.save(observation);
 
        AstronomicalObservation retrievedObservation = astronomicalObservationRepository
            .findById(savedObservation.getId())
            .orElseThrow(IllegalStateException::new);
        assertThat(retrievedObservation.getNextExpectedAppearance())
            .isEqualTo(nextExpectedAppearence);
    }

    @Test
    void whenNormalizeUTCStorageStrategyUsed_thenTimestampNormalizedToUTCSaved() {
        OffsetDateTime lastRecordedSighting = OffsetDateTime.of(1999, 12, 25, 18, 0, 0, 0, ZoneOffset.ofHours(8));
        AstronomicalObservation observation = new AstronomicalObservation();
        observation.setId(UUID.randomUUID());
        observation.setCelestialObjectName("test-planet");
        observation.setLastRecordedSighting(lastRecordedSighting);
        AstronomicalObservation savedObservation = astronomicalObservationRepository.save(observation);
 
        AstronomicalObservation retrievedObservation = astronomicalObservationRepository
            .findById(savedObservation.getId())
            .orElseThrow(IllegalStateException::new);
        assertThat(retrievedObservation.getLastRecordedSighting())
            .isEqualTo(lastRecordedSighting);
    }

}