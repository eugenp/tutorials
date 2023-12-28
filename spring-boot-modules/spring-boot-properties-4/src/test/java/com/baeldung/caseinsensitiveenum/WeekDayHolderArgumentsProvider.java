package com.baeldung.caseinsensitiveenum;


import static com.baeldung.caseinsensitiveenum.SimpleWeekDays.FRIDAY;
import static com.baeldung.caseinsensitiveenum.SimpleWeekDays.MONDAY;
import static com.baeldung.caseinsensitiveenum.SimpleWeekDays.SATURDAY;
import static com.baeldung.caseinsensitiveenum.SimpleWeekDays.SUNDAY;
import static com.baeldung.caseinsensitiveenum.SimpleWeekDays.THURSDAY;
import static com.baeldung.caseinsensitiveenum.SimpleWeekDays.TUESDAY;
import static com.baeldung.caseinsensitiveenum.SimpleWeekDays.WEDNESDAY;

import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class WeekDayHolderArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
        return Stream.of(
            Arguments.of(((Function<WeekDaysHolder, SimpleWeekDays>) WeekDaysHolder::getMonday), MONDAY),
            Arguments.of(((Function<WeekDaysHolder, SimpleWeekDays>) WeekDaysHolder::getTuesday), TUESDAY),
            Arguments.of(((Function<WeekDaysHolder, SimpleWeekDays>) WeekDaysHolder::getWednesday), WEDNESDAY),
            Arguments.of(((Function<WeekDaysHolder, SimpleWeekDays>) WeekDaysHolder::getThursday), THURSDAY),
            Arguments.of(((Function<WeekDaysHolder, SimpleWeekDays>) WeekDaysHolder::getFriday), FRIDAY),
            Arguments.of(((Function<WeekDaysHolder, SimpleWeekDays>) WeekDaysHolder::getSaturday), SATURDAY),
            Arguments.of(((Function<WeekDaysHolder, SimpleWeekDays>) WeekDaysHolder::getSunday), SUNDAY)
        );
    }
}
