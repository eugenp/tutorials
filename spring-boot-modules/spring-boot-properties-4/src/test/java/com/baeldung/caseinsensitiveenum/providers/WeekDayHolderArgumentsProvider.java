package com.baeldung.caseinsensitiveenum.providers;


import static com.baeldung.caseinsensitiveenum.week.WeekDays.FRIDAY;
import static com.baeldung.caseinsensitiveenum.week.WeekDays.MONDAY;
import static com.baeldung.caseinsensitiveenum.week.WeekDays.SATURDAY;
import static com.baeldung.caseinsensitiveenum.week.WeekDays.SUNDAY;
import static com.baeldung.caseinsensitiveenum.week.WeekDays.THURSDAY;
import static com.baeldung.caseinsensitiveenum.week.WeekDays.TUESDAY;
import static com.baeldung.caseinsensitiveenum.week.WeekDays.WEDNESDAY;

import com.baeldung.caseinsensitiveenum.week.WeekDays;
import com.baeldung.caseinsensitiveenum.week.WeekDaysHolder;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class WeekDayHolderArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
        return Stream.of(
            Arguments.of(((Function<WeekDaysHolder, WeekDays>) WeekDaysHolder::getMonday), MONDAY),
            Arguments.of(((Function<WeekDaysHolder, WeekDays>) WeekDaysHolder::getTuesday), TUESDAY),
            Arguments.of(((Function<WeekDaysHolder, WeekDays>) WeekDaysHolder::getWednesday), WEDNESDAY),
            Arguments.of(((Function<WeekDaysHolder, WeekDays>) WeekDaysHolder::getThursday), THURSDAY),
            Arguments.of(((Function<WeekDaysHolder, WeekDays>) WeekDaysHolder::getFriday), FRIDAY),
            Arguments.of(((Function<WeekDaysHolder, WeekDays>) WeekDaysHolder::getSaturday), SATURDAY),
            Arguments.of(((Function<WeekDaysHolder, WeekDays>) WeekDaysHolder::getSunday), SUNDAY)
        );
    }
}
