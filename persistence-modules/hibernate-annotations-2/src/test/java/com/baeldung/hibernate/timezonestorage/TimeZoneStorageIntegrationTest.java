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

import net.bytebuddy.utility.RandomString;

@SpringBootTest(classes = Application.class)
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
class TimeZoneStorageIntegrationTest {

    @Autowired
    private AstronomicalObservationRepository astronomicalObservationRepository;

    @Test
    void whenNativeStorageStrategyUsed_ThenTimestampWithTimezoneSaved() {
        AstronomicalObservation observation = new AstronomicalObservation();
        observation.setId(UUID.randomUUID());
        observation.setCelestialObjectName(RandomString.make());
        observation.setObservationStartTime(ZonedDateTime.now());

        AstronomicalObservation savedObservation = astronomicalObservationRepository.save(observation);
        assertThat(savedObservation)
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(observation);
    }

    @Test
    void whenColumnStorageStrategyUsed_ThenTimestampAndOffsetSaved() {
        AstronomicalObservation observation = new AstronomicalObservation();
        observation.setId(UUID.randomUUID());
        observation.setCelestialObjectName(RandomString.make());
        observation.setPeakVisibilityTime(OffsetDateTime.now());
        AstronomicalObservation savedObservation = astronomicalObservationRepository.save(observation);

        AstronomicalObservation retrievedObservation = astronomicalObservationRepository
            .findById(savedObservation.getId())
            .orElseThrow(IllegalStateException::new);
        assertThat(retrievedObservation)
            .isNotNull()
            .satisfies(obs -> {
                assertThat(obs.getPeakVisibilityTimeOffset())
                    .isNotNull();
            })
            .usingRecursiveComparison()
            .ignoringFields("peakVisibilityTimeOffset")
            .isEqualTo(savedObservation);
    }

    @Test
    void whenNormalizeStorageStrategyUsed_thenTimestampNormalizedToLocalTimezoneSaved() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata")); // UTC+05:30
 
        AstronomicalObservation observation = new AstronomicalObservation();
        observation.setId(UUID.randomUUID());
        observation.setCelestialObjectName(RandomString.make());
        observation.setNextExpectedAppearance(ZonedDateTime.of(1999, 12, 25, 18, 0, 0, 0, ZoneId.of("UTC+8")));
        AstronomicalObservation savedObservation = astronomicalObservationRepository.save(observation);
 
        AstronomicalObservation retrievedObservation = astronomicalObservationRepository
            .findById(savedObservation.getId())
            .orElseThrow(IllegalStateException::new);
        assertThat(retrievedObservation)
            .isNotNull()
            .usingRecursiveComparison()
            .isNotEqualTo(savedObservation)
            .ignoringFields("nextExpectedAppearance")
            .isEqualTo(savedObservation);
    }

    @Test
    void whenNormalizeUTCStorageStrategyUsed_thenTimestampNormalizedToUTCSaved() {        
        AstronomicalObservation observation = new AstronomicalObservation();
        observation.setId(UUID.randomUUID());
        observation.setCelestialObjectName(RandomString.make());
        observation.setLastRecordedSighting(OffsetDateTime.of(1999, 12, 25, 18, 0, 0, 0, ZoneOffset.ofHours(8)));
        AstronomicalObservation savedObservation = astronomicalObservationRepository.save(observation);
 
        AstronomicalObservation retrievedObservation = astronomicalObservationRepository
            .findById(savedObservation.getId())
            .orElseThrow(IllegalStateException::new);
        assertThat(retrievedObservation)
            .isNotNull()
            .usingRecursiveComparison()
            .isNotEqualTo(savedObservation)
            .ignoringFields("lastRecordedSighting")
            .isEqualTo(savedObservation);
    }

}