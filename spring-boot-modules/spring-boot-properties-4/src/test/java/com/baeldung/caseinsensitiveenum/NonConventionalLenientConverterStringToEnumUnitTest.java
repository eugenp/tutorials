package com.baeldung.caseinsensitiveenum;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "non_conventional.monday=Mon-Day!",
    "non_conventional.tuesday=TuesDAY#",
    "non_conventional.wednesday=Wednes@day",
    "non_conventional.thursday=THURSday^",
    "non_conventional.friday=Fri:Day_%",
    "non_conventional.saturday=Satur_DAY*",
    "non_conventional.sunday=Sun+Day",
}, classes = NonConventionalWeekDaysHolder.class)
class NonConventionalLenientConverterStringToEnumUnitTest {

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
