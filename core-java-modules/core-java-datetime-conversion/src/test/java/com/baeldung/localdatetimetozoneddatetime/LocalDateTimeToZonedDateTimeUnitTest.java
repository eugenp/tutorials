import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class LocalDateTimeToZonedDateTimeUnitTest {

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithAtZoneMethod_shouldConvert(){
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 30, 22);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Canada/Atlantic"));


        assertNotEquals(localDateTime.getYear(), zonedDateTime.getYear());
        assertNotEquals(localDateTime.getMonth(), zonedDateTime.getMonth());
        assertNotEquals(localDateTime.getDayOfMonth(), zonedDateTime.getDayOfMonth());
        assertNotEquals(localDateTime.getHour(), zonedDateTime.getHour());

    }

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithOfMethod_shouldConvert(){
        LocalDateTime localDateTime = LocalDateTime.of(2022, 11, 5, 7, 30, 22);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Canada/Atlantic"));

        assertEquals(localDateTime.getYear(), zonedDateTime.getYear());
        assertEquals(localDateTime.getMonth(), zonedDateTime.getMonth());
        assertEquals(localDateTime.getDayOfMonth(), zonedDateTime.getDayOfMonth());
        assertNotEquals(localDateTime.getHour(), zonedDateTime.getHour());

    }

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithOfInstantMethod_shouldConvert(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.of("Africa/Lagos");
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(localDateTime, zoneOffset, zoneId);

        assertEquals(localDateTime.getYear(), zonedDateTime.getYear());
        assertEquals(localDateTime.getMonth(), zonedDateTime.getMonth());
        assertEquals(localDateTime.getDayOfMonth(), zonedDateTime.getDayOfMonth());
        assertEquals(localDateTime.getHour(), zonedDateTime.getHour());
        assertEquals(localDateTime.getMinute(), zonedDateTime.getMinute());
        assertEquals(localDateTime.getSecond(), zonedDateTime.getSecond());

    }

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithOfLocalMethod_shouldConvert(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.of("Africa/Lagos");
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofLocal(localDateTime, zoneId, zoneOffset);

        assertEquals(localDateTime.getYear(), zonedDateTime.getYear());
        assertEquals(localDateTime.getMonth(), zonedDateTime.getMonth());
        assertEquals(localDateTime.getDayOfMonth(), zonedDateTime.getDayOfMonth());
        assertEquals(localDateTime.getHour(), zonedDateTime.getHour());
        assertEquals(localDateTime.getMinute(), zonedDateTime.getMinute());
        assertEquals(localDateTime.getSecond(), zonedDateTime.getSecond());

    }

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithOfStrictMethod_shouldConvert(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofStrict(localDateTime, zoneOffset, zoneId);

        assertEquals(localDateTime.getYear(), zonedDateTime.getYear());
        assertEquals(localDateTime.getMonth(), zonedDateTime.getMonth());
        assertEquals(localDateTime.getDayOfMonth(), zonedDateTime.getDayOfMonth());
        assertEquals(localDateTime.getHour(), zonedDateTime.getHour());
        assertEquals(localDateTime.getMinute(), zonedDateTime.getMinute());
        assertEquals(localDateTime.getSecond(), zonedDateTime.getSecond());

    }

    @Test
    void whenConvertLocalDateTimeToZonedDateTimeWithInvalidZoneOffSet_shouldThrowDateTimeException(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        assertThrows(DateTimeException.class, () -> ZonedDateTime.ofStrict(localDateTime, zoneOffset, zoneId));

    }


    @Test
    void whenConvertZonedDateTimeToLocalDateTimeWithToLocalDateTimeMethod_shouldConvert(){
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        assertEquals(zonedDateTime.getYear(), localDateTime.getYear());
        assertEquals(zonedDateTime.getMonth(), localDateTime.getMonth());
        assertEquals(zonedDateTime.getDayOfMonth(), localDateTime.getDayOfMonth());
        assertEquals(zonedDateTime.getHour(), localDateTime.getHour());
        assertEquals(zonedDateTime.getMinute(), localDateTime.getMinute());
        assertEquals(zonedDateTime.getSecond(), localDateTime.getSecond());
    }


}