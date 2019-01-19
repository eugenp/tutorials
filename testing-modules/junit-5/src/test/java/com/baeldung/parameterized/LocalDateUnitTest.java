package com.baeldung.parameterized;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalDateUnitTest {

    @ParameterizedTest
    @CsvSource({"2018/12/25,2018", "2019/02/11,2019"})
    void getYear_ShouldWorkAsExpected(@ConvertWith(SlashyDateConverter.class) LocalDate date, int expected) {
        assertEquals(expected, date.getYear());
    }
}
