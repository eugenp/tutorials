package com.baeldung.caseinsensitiveenum;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.caseinsensitiveenum.StrictConverterStringToEnumUnitTest.WeekDayConverterConfiguration;
import java.util.function.Function;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@SpringBootTest(properties = {
    "strict.monday=Mon-Day!",
    "strict.tuesday=TuesDAY#",
    "strict.wednesday=Wednes@day",
    "strict.thursday=THURSday^",
    "strict.friday=Fri:Day_%",
    "strict.saturday=Satur_DAY*",
    "strict.sunday=Sun+Day",
}, classes = {StrictWeekDaysHolder.class, WeekDayConverterConfiguration.class})
class StrictConverterStringToEnumUnitTest {

    public static class WeekDayConverterConfiguration {
        @Bean
        public ConversionService conversionService() {
            final DefaultConversionService defaultConversionService = new DefaultConversionService();
            defaultConversionService.addConverter(new StrictNullableWeekDayConverter());
            return defaultConversionService;
        }
    }

    @Autowired
    private StrictWeekDaysHolder lenientHolder;

    @ParameterizedTest
    @ArgumentsSource(WeekDayHolderArgumentsProvider.class)
    void givenPropertiesWhenInjectEnumThenValueIsNull(
        Function<WeekDaysHolder, SimpleWeekDays> methodReference, SimpleWeekDays ignored) {
        SimpleWeekDays actual = methodReference.apply(lenientHolder);
        assertThat(actual).isNull();
    }
}
