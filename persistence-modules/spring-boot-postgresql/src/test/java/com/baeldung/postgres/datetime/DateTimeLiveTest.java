package com.baeldung.postgres.datetime;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class DateTimeLiveTest {

    @Autowired
    private DateTimeValueRepository dateTimeValueRepository;

    @Test
    public void givenJavaDateTimesValues_whenPersisted_thenAllValuesAreStored() {

        // Given
        DateTimeValues dateTimeValues = new DateTimeValues();

        // When
        dateTimeValues = dateTimeValueRepository.save(dateTimeValues);
        DateTimeValues persisted = dateTimeValueRepository.findById(dateTimeValues.getId()).get();

        // Then
        assertNotNull(persisted);
    }

    @Test
    public void givenJavaInstant_whenPersistedAsSqlDate_thenRetrievedWithoutTime() {
        DateTimeValues dateTimeValues = new DateTimeValues();
        DateTimeValues persisted = dateTimeValueRepository.save(dateTimeValues);
        DateTimeValues fromDatabase = dateTimeValueRepository.findById(persisted.getId()).get();

        Assertions.assertNotEquals(dateTimeValues.getInstantAsDate(), fromDatabase.getInstantAsDate());
        Assertions.assertEquals(dateTimeValues.getInstantAsDate().truncatedTo(ChronoUnit.DAYS), fromDatabase.getInstantAsDate());
    }
}
