package com.baeldung.caseinsensitiveenum;

import static com.baeldung.caseinsensitiveenum.WeekDays.FRIDAY;
import static com.baeldung.caseinsensitiveenum.WeekDays.MONDAY;
import static com.baeldung.caseinsensitiveenum.WeekDays.SATURDAY;
import static com.baeldung.caseinsensitiveenum.WeekDays.SUNDAY;
import static com.baeldung.caseinsensitiveenum.WeekDays.THURSDAY;
import static com.baeldung.caseinsensitiveenum.WeekDays.TUESDAY;
import static com.baeldung.caseinsensitiveenum.WeekDays.WEDNESDAY;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "lenient.monday=Mon-Day!",
    "lenient.tuesday=TuesDAY#",
    "lenient.wednesday=Wednes@day",
    "lenient.thursday=THURSday^",
    "lenient.friday=Fri:Day_%",
    "lenient.saturday=Satur_DAY*",
    "lenient.sunday=Sun+Day",
})
class LenientConverterStringToEnumUnitTest {

    @Autowired
    private LenientWeekDaysHolder propertyHolder;

    @ParameterizedTest
    @MethodSource
    void givenPropertiesWhenInjectEnumThenValueIsPresent(
        Function<LenientWeekDaysHolder, WeekDays> methodReference, WeekDays expected) {
        WeekDays actual = methodReference.apply(propertyHolder);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenPropertiesWhenInjectEnumThenValueIsPresent() {
        return Stream.of(
          Arguments.of(((Function<LenientWeekDaysHolder, WeekDays>) LenientWeekDaysHolder::getMonday), MONDAY),
          Arguments.of(((Function<LenientWeekDaysHolder, WeekDays>) LenientWeekDaysHolder::getTuesday), TUESDAY),
          Arguments.of(((Function<LenientWeekDaysHolder, WeekDays>) LenientWeekDaysHolder::getWednesday), WEDNESDAY),
          Arguments.of(((Function<LenientWeekDaysHolder, WeekDays>) LenientWeekDaysHolder::getThursday), THURSDAY),
          Arguments.of(((Function<LenientWeekDaysHolder, WeekDays>) LenientWeekDaysHolder::getFriday), FRIDAY),
          Arguments.of(((Function<LenientWeekDaysHolder, WeekDays>) LenientWeekDaysHolder::getSaturday), SATURDAY),
          Arguments.of(((Function<LenientWeekDaysHolder, WeekDays>) LenientWeekDaysHolder::getSunday), SUNDAY)
        );
    }
}
