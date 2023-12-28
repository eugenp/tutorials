package com.baeldung.caseinsensitiveenum;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "monday=Mon-Day!",
    "tuesday=TuesDAY#",
    "wednesday=Wednes@day",
    "thursday=THURSday^",
    "friday=Fri:Day_%",
    "saturday=Satur_DAY*",
    "sunday=Sun+Day",
}, classes = LenientWeekDaysHolder.class)
class LenientConverterStringToEnumUnitTest {

    @Autowired
    private LenientWeekDaysHolder propertyHolder;

    @ParameterizedTest
    @ArgumentsSource(WeekDayHolderArgumentsProvider.class)
    void givenPropertiesWhenInjectEnumThenValueIsPresent(
        Function<WeekDaysHolder, SimpleWeekDays> methodReference, SimpleWeekDays expected) {
        SimpleWeekDays actual = methodReference.apply(propertyHolder);
        assertThat(actual).isEqualTo(expected);
    }
}
