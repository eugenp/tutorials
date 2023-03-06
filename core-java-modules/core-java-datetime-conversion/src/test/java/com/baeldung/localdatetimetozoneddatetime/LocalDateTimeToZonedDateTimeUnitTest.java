import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class LocalDateTimeToZonedDateTimeUnitTest {

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithAtZoneMethod_shouldConvertAndMaintainDateTimeValues(){
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 30, 22);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Canada/Atlantic"));

        assertEquals(localDateTime.getYear(), zonedDateTime.getYear());
        assertEquals(localDateTime.getMonth(), zonedDateTime.getMonth());
        assertEquals(localDateTime.getDayOfMonth(), zonedDateTime.getDayOfMonth());
        assertEquals(localDateTime.getHour(), zonedDateTime.getHour());
        assertEquals(localDateTime.getMinute(), zonedDateTime.getMinute());
        assertEquals(localDateTime.getSecond(), zonedDateTime.getSecond());
    }

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithAtZoneAndwithZoneSameInstantMethods_shouldConvert(){
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 30, 22);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Africa/Lagos")).withZoneSameInstant(ZoneId.of("Canada/Atlantic"));

        assertEquals("2021-12-31T19:30:22-04:00[Canada/Atlantic]", zonedDateTime.toString());
        assertEquals("-04:00", zonedDateTime.getOffset().toString());
    }

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithOfMethod_shouldConvert(){
        LocalDateTime localDateTime = LocalDateTime.of(2022, 11, 5, 7, 30, 22);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("Africa/Accra")).withZoneSameInstant(ZoneId.of("Africa/Lagos"));

        assertEquals("2022-11-05T08:30:22+01:00[Africa/Lagos]", zonedDateTime.toString());
        assertEquals(localDateTime.getYear(), zonedDateTime.getYear());
    }

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithOfInstantMethod_shouldConvert(){
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 5, 17, 30, 22);
        ZoneId zoneId = ZoneId.of("Africa/Lagos");
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(localDateTime, zoneOffset, zoneId);

        assertEquals("2022-01-05T17:30:22+01:00[Africa/Lagos]", zonedDateTime.toString());
    }

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithOfLocalMethod_shouldConvert(){
        LocalDateTime localDateTime = LocalDateTime.of(2022, 8, 25, 8, 35, 22);
        ZoneId zoneId = ZoneId.of("Africa/Lagos");
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofLocal(localDateTime, zoneId, zoneOffset);

        assertEquals("2022-08-25T08:35:22+01:00[Africa/Lagos]", zonedDateTime.toString());
    }

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithOfStrictMethod_shouldConvert(){
        LocalDateTime localDateTime = LocalDateTime.of(2002, 12, 25, 6, 18, 2);
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofStrict(localDateTime, zoneOffset, zoneId);

        assertEquals("2002-12-25T06:18:02+09:00[Asia/Tokyo]", zonedDateTime.toString());
    }

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithInvalidZoneOffSet_shouldThrowDateTimeException(){
        LocalDateTime localDateTime = LocalDateTime.of(2022, 12, 25, 6, 18, 2);;
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        assertThrows(DateTimeException.class, () -> ZonedDateTime.ofStrict(localDateTime, zoneOffset, zoneId));
    }


    @Test
    void whenConvertZonedDateTimeToLocalDateTimeWithToLocalDateTimeMethod_shouldConvert(){
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2011, 2, 12, 6, 14, 1, 58086000, ZoneId.of("Asia/Tokyo"));
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        assertEquals("2011-02-12T06:14:01.058086+09:00[Asia/Tokyo]", zonedDateTime.toString());
    }
}