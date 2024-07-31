package com.baeldung.caseinsensitiveenum;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.caseinsensitiveenum.CaseInsensitiveStringToEnumConverterUnitTest.WeekDayConverterConfiguration;
import com.baeldung.caseinsensitiveenum.converter.StrictNullableWeekDayConverter;
import com.baeldung.caseinsensitiveenum.providers.SpELWeekDayHolderArgumentsProvider;
import com.baeldung.caseinsensitiveenum.week.SpELWeekDaysHolder;
import com.baeldung.caseinsensitiveenum.week.WeekDays;
import java.util.function.Function;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@SpringBootTest(properties = {
    "monday=monday",
    "tuesday=tuesday",
    "wednesday=wednesday",
    "thursday=THURSDAY",
    "friday=Friday",
    "saturday=saturDAY",
    "sunday=sUndAy",
}, classes = {SpELWeekDaysHolder.class, WeekDayConverterConfiguration.class})
class SpELCaseInsensitiveStringToEnumConverterUnitTest {

    public static class WeekDayConverterConfiguration {
        @Bean
        public ConversionService conversionService() {
            final DefaultConversionService defaultConversionService = new DefaultConversionService();
            defaultConversionService.addConverter(new StrictNullableWeekDayConverter());
            return defaultConversionService;
        }
    }

    @Autowired
    private SpELWeekDaysHolder holder;

    @ParameterizedTest
    @ArgumentsSource(SpELWeekDayHolderArgumentsProvider.class)
    void givenPropertiesWhenInjectEnumThenValueIsNull(
        Function<SpELWeekDaysHolder, WeekDays> methodReference, WeekDays expected) {
        WeekDays actual = methodReference.apply(holder);
        assertThat(actual).isEqualTo(expected);
    }
}
