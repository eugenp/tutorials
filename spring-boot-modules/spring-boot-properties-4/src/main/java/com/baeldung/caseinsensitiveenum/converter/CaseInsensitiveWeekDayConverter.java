package com.baeldung.caseinsensitiveenum.converter;

import com.baeldung.caseinsensitiveenum.week.SimpleWeekDays;
import org.springframework.core.convert.converter.Converter;

public class CaseInsensitiveWeekDayConverter implements Converter<String, SimpleWeekDays> {

    @Override
    public SimpleWeekDays convert(final String source) {
        try {
            return SimpleWeekDays.valueOf(source.trim());
        } catch (IllegalArgumentException exception) {
            return SimpleWeekDays.valueOf(source.trim().toUpperCase());
        }
    }
}
