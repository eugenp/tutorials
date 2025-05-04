package com.baeldung.jpa.convertdates;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class LocalDateTimeToSqlDateUnitTest {

    @Test
    void givenALocalDateTime_whenConvertingToSqlDate_thenReturnSqlDateWithoutTime() {
        LocalDateTime givenLocalDateTime = LocalDateTime.parse("2024-05-06T14:02:22.214");
        java.sql.Date expectedSqlDate = java.sql.Date.valueOf("2024-05-06");

        java.sql.Date sqlDate = java.sql.Date.valueOf(givenLocalDateTime.toLocalDate());

        assertThat(sqlDate).isEqualTo(expectedSqlDate);
    }

}
