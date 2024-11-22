package com.baeldung.postgres.datetime;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class DateTimeIntegrationTest {

    @Autowired
    private DateTimeValueRepository dateTimeValueRepository;

    @Test
    public void when_persisted_then_all_values_are_stored() {

        // Given
        DateTimeValues dateTimeValues = new DateTimeValues();

        // When
        dateTimeValues = dateTimeValueRepository.save(dateTimeValues);
        DateTimeValues persisted = dateTimeValueRepository.findById(dateTimeValues.getId()).get();

        // Then
        assertNotNull(persisted);
    }

    @Test
    public void given_persisted_local_date_time_as_date_then_retrieved_without_time() {
        DateTimeValues dateTimeValues = new DateTimeValues();
        DateTimeValues persisted = dateTimeValueRepository.save(dateTimeValues);
        DateTimeValues fromDatabase = dateTimeValueRepository.findById(persisted.getId()).get();
        Assertions.assertNotEquals(dateTimeValues.getInstantAsDate(), fromDatabase.getInstantAsDate());
    }
}
