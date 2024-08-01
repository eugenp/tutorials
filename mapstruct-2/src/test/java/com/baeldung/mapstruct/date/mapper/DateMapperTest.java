package com.baeldung.mapstruct.date.mapper;

import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateMapperTest {

    private final DateMapper dateMapper = new DateMapper();

    @Test
    public void whenMappingStringToDate_thenCorrectDate() throws ParseException {
        String dateString = "2024-08-01";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedDate = dateFormat.parse(dateString);

        Date actualDate = dateMapper.mapStringToDate(dateString);

        assertEquals(expectedDate, actualDate);
    }


    @Test
    public void whenMappingInvalidStringToDate_thenThrowsException() {
        String invalidDateString = "invalid-date";

        assertThrows(RuntimeException.class, () -> {
            dateMapper.mapStringToDate(invalidDateString);
        });
    }
}