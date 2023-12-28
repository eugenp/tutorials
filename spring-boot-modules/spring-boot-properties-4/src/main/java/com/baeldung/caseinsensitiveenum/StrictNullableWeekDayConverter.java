package com.baeldung.caseinsensitiveenum;

import org.springframework.core.convert.converter.Converter;

public class StrictNullableWeekDayConverter implements Converter<String, SimpleWeekDays> {
    @Override
    public SimpleWeekDays convert(final String source) {
        try {
            return SimpleWeekDays.valueOf(source.trim());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
