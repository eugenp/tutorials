package com.baeldung.gregorian.calendar;

import static org.junit.Assert.*;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

import com.baeldung.gregorian.calendar.GregorianCalendarExample;

public class GregorianCalendarTester {

    @Test
    public void test_Calendar_Return_Type_Valid() {
        Calendar calendar = Calendar.getInstance();
        assert ("gregory".equals(calendar.getCalendarType()));
    }

    @Test
    public void test_Calendar_Return_Type_InValid() {
        Calendar calendar = Calendar.getInstance();
        assertNotEquals("gregorys", calendar.getCalendarType());
    }

    @Test(expected = ClassCastException.class)
    public void test_Class_Cast_Exception() {
        TimeZone tz = TimeZone.getTimeZone("GMT+9:00");
        Locale loc = new Locale("ja", "JP", "JP");
        Calendar calendar = Calendar.getInstance(loc);
        GregorianCalendar gc = (GregorianCalendar) calendar;
    }

    @Test
    public void test_Getting_Calendar_information() {
        GregorianCalendar calendar = new GregorianCalendar(2018, 5, 28);
        assertTrue(false == calendar.isLeapYear(calendar.YEAR));
        assertTrue(52 == calendar.getWeeksInWeekYear());
        assertTrue(2018 == calendar.getWeekYear());
        assertTrue(30 == calendar.getActualMaximum(calendar.DAY_OF_MONTH));
        assertTrue(1 == calendar.getActualMinimum(calendar.DAY_OF_MONTH));
        assertTrue(1 == calendar.getGreatestMinimum(calendar.DAY_OF_MONTH));
        assertTrue(28 == calendar.getLeastMaximum(calendar.DAY_OF_MONTH));
        assertTrue(31 == calendar.getMaximum(calendar.DAY_OF_MONTH));
        assertTrue(1 == calendar.getMinimum(calendar.DAY_OF_MONTH));
        assertTrue(52 == calendar.getWeeksInWeekYear());
    }

    @Test
    public void test_Compare_Date_FirstDate_Greater_SecondDate() {
        GregorianCalendar firstDate = new GregorianCalendar(2018, 6, 28);
        GregorianCalendar secondDate = new GregorianCalendar(2018, 5, 28);
        assertTrue(1 == firstDate.compareTo(secondDate));
    }


    @Test
    public void test_Compare_Date_FirstDate_Smaller_SecondDate() {
        GregorianCalendar firstDate = new GregorianCalendar(2018, 5, 28);
        GregorianCalendar secondDate = new GregorianCalendar(2018, 6, 28);
        assertTrue(-1 == firstDate.compareTo(secondDate));
    }

    @Test
    public void test_Compare_Date_Both_Dates_Equal() {
        GregorianCalendar firstDate = new GregorianCalendar(2018, 6, 28);
        GregorianCalendar secondDate = new GregorianCalendar(2018, 6, 28);
        assertTrue(0 == firstDate.compareTo(secondDate));
    }

    @Test
    public void test_date_format() {
        GregorianCalendarExample calendarDemo = new GregorianCalendarExample();
        GregorianCalendar calendar = new GregorianCalendar(2018, 6, 28);
        assertEquals("28 Jul 2018", calendarDemo.formatDate(calendar));
    }

    @Test
    public void test_dateFormatdMMMuuuu() {
        String expectedDate = new GregorianCalendar(2018, 6, 28).toZonedDateTime()
            .format(DateTimeFormatter.ofPattern("d MMM uuuu"));
        assertEquals("28 Jul 2018", expectedDate);
    }

    @Test
    public void test_addDays() {
        GregorianCalendarExample calendarDemo = new GregorianCalendarExample();
        GregorianCalendar calendarActual = new GregorianCalendar(2018, 6, 28);
        GregorianCalendar calendarExpected = new GregorianCalendar(2018, 6, 28);
        calendarExpected.add(Calendar.DATE, 1);
        Date expectedDate = calendarExpected.getTime();
        assertEquals(expectedDate, calendarDemo.addDays(calendarActual, 1));
    }

    @Test
    public void test_whenAddOneDay_thenMonthIsChanged() {
        final int finalDay1 = 1;
        final int finalMonthJul = 6; 
        GregorianCalendar calendarExpected = new GregorianCalendar(2018, 5, 30);
        calendarExpected.add(Calendar.DATE, 1);
        System.out.println(calendarExpected.getTime());
        assertEquals(calendarExpected.get(Calendar.DATE), finalDay1);
        assertEquals(calendarExpected.get(Calendar.MONTH), finalMonthJul);
    }

    @Test
    public void test_whenSubtractOneDay_thenMonthIsChanged() {
        final int finalDay31 = 31;
        final int finalMonthMay = 4; 
        GregorianCalendar calendarExpected = new GregorianCalendar(2018, 5, 1);
        calendarExpected.add(Calendar.DATE, -1);
        assertEquals(calendarExpected.get(Calendar.DATE), finalDay31);
        assertEquals(calendarExpected.get(Calendar.MONTH), finalMonthMay);
    }

    @Test
    public void test_subDays() {
        GregorianCalendarExample calendarDemo = new GregorianCalendarExample();
        GregorianCalendar calendarActual = new GregorianCalendar(2018, 6, 28);
        GregorianCalendar calendarExpected = new GregorianCalendar(2018, 6, 28);
        calendarExpected.add(Calendar.DATE, -1);
        Date expectedDate = calendarExpected.getTime();
        assertEquals(expectedDate, calendarDemo.subtractDays(calendarActual, 1));
    }

    @Test
    public void test_rollAdd() {
        GregorianCalendarExample calendarDemo = new GregorianCalendarExample();
        GregorianCalendar calendarActual = new GregorianCalendar(2018, 6, 28);
        GregorianCalendar calendarExpected = new GregorianCalendar(2018, 6, 28);
        calendarExpected.roll(Calendar.MONTH, 8);
        Date expectedDate = calendarExpected.getTime();
        assertEquals(expectedDate, calendarDemo.rollAdd(calendarActual, 8));
    }

    @Test
    public void test_whenRollUpOneMonth_thenYearIsUnchanged() {
        final int rolledUpMonthJuly = 7, orginalYear2018 = 2018;
        GregorianCalendar calendarExpected = new GregorianCalendar(2018, 6, 28);
        calendarExpected.roll(Calendar.MONTH, 1);
        assertEquals(calendarExpected.get(Calendar.MONTH), rolledUpMonthJuly);
        assertEquals(calendarExpected.get(Calendar.YEAR), orginalYear2018);
    }

    @Test
    public void test_whenRollDownOneMonth_thenYearIsUnchanged() {
        final int rolledDownMonthJune = 5, orginalYear2018 = 2018;
        GregorianCalendar calendarExpected = new GregorianCalendar(2018, 6, 28);
        calendarExpected.roll(Calendar.MONTH, -1);
        assertEquals(calendarExpected.get(Calendar.MONTH), rolledDownMonthJune);
        assertEquals(calendarExpected.get(Calendar.YEAR), orginalYear2018);
    }

    @Test
    public void test_rollSubtract() {
        GregorianCalendarExample calendarDemo = new GregorianCalendarExample();
        GregorianCalendar calendarActual = new GregorianCalendar(2018, 6, 28);
        GregorianCalendar calendarExpected = new GregorianCalendar(2018, 6, 28);
        calendarExpected.roll(Calendar.MONTH, -8);
        Date expectedDate = calendarExpected.getTime();
        assertEquals(expectedDate, calendarDemo.rollAdd(calendarActual, -8));
    }

    @Test
    public void test_setMonth() {
        GregorianCalendarExample calendarDemo = new GregorianCalendarExample();
        GregorianCalendar calendarActual = new GregorianCalendar(2018, 6, 28);
        GregorianCalendar calendarExpected = new GregorianCalendar(2018, 6, 28);
        calendarExpected.set(Calendar.MONTH, 3);
        Date expectedDate = calendarExpected.getTime();
        assertEquals(expectedDate, calendarDemo.setMonth(calendarActual, 3));
    }

    @Test
    public void test_setMonthApril() {
        final int setMonthApril = 3, orginalYear2018 = 2018, originalDate28 = 28;
        GregorianCalendar calendarExpected = new GregorianCalendar(2018, 6, 28);
        calendarExpected.set(Calendar.MONTH, 3);
        assertEquals(calendarExpected.get(Calendar.MONTH), setMonthApril);
        assertEquals(calendarExpected.get(Calendar.YEAR), orginalYear2018);
        assertEquals(calendarExpected.get(Calendar.DATE), originalDate28);
    }

    @Test
    public void test_toXMLGregorianCalendar() throws DatatypeConfigurationException {
        GregorianCalendarExample calendarDemo = new GregorianCalendarExample();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        GregorianCalendar calendarActual = new GregorianCalendar(2018, 6, 28);
        GregorianCalendar calendarExpected = new GregorianCalendar(2018, 6, 28);
        XMLGregorianCalendar expectedXMLGregorianCalendar = datatypeFactory
            .newXMLGregorianCalendar(calendarExpected);
        assertEquals(expectedXMLGregorianCalendar, 
            calendarDemo.toXMLGregorianCalendar(calendarActual));
    }

    @Test
    public void test_isLeapYear_True() {
        GregorianCalendarExample calendarDemo = new GregorianCalendarExample();
        assertEquals(true, calendarDemo.isLeapYearExample(2016));

    }

    @Test
    public void test_isLeapYear_False() {
        GregorianCalendarExample calendarDemo = new GregorianCalendarExample();
        assertEquals(false, calendarDemo.isLeapYearExample(2018));

    }
    
    @Test
    public void test_toDate() throws DatatypeConfigurationException {
        GregorianCalendar calendarActual = new GregorianCalendar(2018, 6, 28);
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar expectedXMLGregorianCalendar = datatypeFactory
            .newXMLGregorianCalendar(calendarActual);
        expectedXMLGregorianCalendar.toGregorianCalendar().getTime();
        assertEquals(calendarActual.getTime(), 
            expectedXMLGregorianCalendar.toGregorianCalendar().getTime() );
    }
}
