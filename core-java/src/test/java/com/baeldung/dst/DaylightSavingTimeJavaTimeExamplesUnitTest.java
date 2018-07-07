package com.baeldung.dst;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import org.junit.Test;

public class DaylightSavingTimeJavaTimeExamplesUnitTest {

    @Test
    public void givenItalianTimeZone_WhenDSTHappens_ThenCorrectlyShiftTimeZone() throws ParseException {
        ZoneId italianZoneId = ZoneId.of("Europe/Rome");

        LocalDateTime localDateTimeBeforeDST = LocalDateTime.of(2018, 3, 25, 1, 55);
        System.out.println(localDateTimeBeforeDST);
        assertThat(localDateTimeBeforeDST.toString()).isEqualTo("2018-03-25T01:55");

        ZonedDateTime zonedDateTimeBeforeDST = localDateTimeBeforeDST.atZone(italianZoneId);
        prettyPrint(zonedDateTimeBeforeDST);
        assertThat(zonedDateTimeBeforeDST.toString()).isEqualTo("2018-03-25T01:55+01:00[Europe/Rome]");

        ZonedDateTime zonedDateTimeAfterDST = zonedDateTimeBeforeDST.plus(10, ChronoUnit.MINUTES);
        prettyPrint(zonedDateTimeAfterDST);
        assertThat(zonedDateTimeAfterDST.toString()).isEqualTo("2018-03-25T03:05+02:00[Europe/Rome]");

        Long deltaBetweenDatesInMinutes = ChronoUnit.MINUTES.between(zonedDateTimeBeforeDST, zonedDateTimeAfterDST);
        assertThat(deltaBetweenDatesInMinutes).isEqualTo(10);

    }

    private void prettyPrint(ZonedDateTime zdt) {
        //@formatter:off
        System.out.println(String.format(
              " ZonedDateTime = %s\n"  
            + "       Zone ID = %s (%s)\n"  
            + "     RawOffset = %s minutes\n"
            + "  -----------------------------------------",
            zdt, zdt.getZone(), zdt.getZone().getId(), zdt.getOffset().getTotalSeconds()/60));
        //@formatter:on
    }

    @Test
    public void whenCounting_ThenPrintDifferencesBetweenAPIs() {
        System.out.println("Total java.time.ZoneId      count : " + ZoneId.getAvailableZoneIds()
            .size());
        System.out.println("Total java.util.TimeZone Id count : " + TimeZone.getAvailableIDs().length);
    }

    
}
