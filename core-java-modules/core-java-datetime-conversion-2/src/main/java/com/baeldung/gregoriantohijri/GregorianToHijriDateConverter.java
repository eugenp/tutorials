package com.baeldung.gregoriantohijri;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.util.GregorianCalendar;

import org.joda.time.chrono.IslamicChronology;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

public class GregorianToHijriDateConverter {
    public static HijrahDate usingHijrahChronology(LocalDate gregorianDate) {
        HijrahChronology hijrahChronology = HijrahChronology.INSTANCE;
        ChronoLocalDate hijriChronoLocalDate = hijrahChronology.date(gregorianDate);
        return HijrahDate.from(hijriChronoLocalDate);
    }

    public static HijrahDate usingFromMethod(LocalDate gregorianDate) {
        return HijrahDate.from(gregorianDate);
    }

    public static org.joda.time.DateTime usingJodaDate(org.joda.time.DateTime gregorianDate) {
        return gregorianDate.withChronology(IslamicChronology.getInstance());
    }

    public static UmmalquraCalendar usingUmmalquraCalendar(GregorianCalendar gregorianCalendar) throws ParseException {
        UmmalquraCalendar hijriCalendar = new UmmalquraCalendar();
        hijriCalendar.setTime(gregorianCalendar.getTime());
        return hijriCalendar;
    }
}
