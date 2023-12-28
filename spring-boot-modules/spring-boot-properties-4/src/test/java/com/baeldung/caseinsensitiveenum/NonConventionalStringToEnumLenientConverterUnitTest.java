package com.baeldung.caseinsensitiveenum;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.caseinsensitiveenum.nonconventionalweek.NonConventionalWeekDays;
import com.baeldung.caseinsensitiveenum.nonconventionalweek.NonConventionalWeekDaysHolder;
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
}, classes = NonConventionalWeekDaysHolder.class)
class NonConventionalStringToEnumLenientConverterUnitTest {

    @Autowired
    private NonConventionalWeekDaysHolder propertyHolder;

    @ParameterizedTest
    @ArgumentsSource(NonConventionalWeekDayHolderArgumentsProvider.class)
    void givenPropertiesWhenInjectEnumThenValueIsPresent(
        Function<NonConventionalWeekDaysHolder, NonConventionalWeekDays> methodReference, NonConventionalWeekDays expected) {
        NonConventionalWeekDays actual = methodReference.apply(propertyHolder);
        assertThat(actual).isEqualTo(expected);
    }
}
