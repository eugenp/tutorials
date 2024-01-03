package com.baeldung.caseinsensitiveenum;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.caseinsensitiveenum.providers.WeekDayHolderArgumentsProvider;
import com.baeldung.caseinsensitiveenum.week.WeekDays;
import com.baeldung.caseinsensitiveenum.week.WeekDaysHolder;
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
}, classes = WeekDaysHolder.class)
class LenientStringToEnumConverterUnitTest {

    @Autowired
    private WeekDaysHolder holder;

    @ParameterizedTest
    @ArgumentsSource(WeekDayHolderArgumentsProvider.class)
    void givenPropertiesWhenInjectEnumThenValueIsPresent(
        Function<WeekDaysHolder, WeekDays> methodReference, WeekDays expected) {
        WeekDays actual = methodReference.apply(holder);
        assertThat(actual).isEqualTo(expected);
    }
}
