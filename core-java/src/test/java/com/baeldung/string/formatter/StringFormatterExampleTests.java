package com.baeldung.string.formatter;

import java.util.Calendar;
import java.util.Formatter;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StringFormatterExampleTests {

    @Test
    public void givenString_whenFormatSpecifierForCalendar_thenGotExpected() {
        //Syntax of Format Specifiers for Date/Time Representation
        Calendar c = Calendar.getInstance();
        String s = String.format("Today's date: %1$tm %1$te,%1$tY", c);
        assertEquals("Today's date: 11 7,2017", s);
    }

    @Test
    public void givenString_whenConversion_thenConvertedString() {
        //General Conversions
        String s = String.format("The correct answer is %s", false);
        assertEquals("The correct answer is false", s);

        s = String.format("The correct answer is %s", false);
        assertEquals("The correct answer is false", s);

        s = String.format("The correct answer is %b", null);
        assertEquals("The correct answer is false", s);

        s = String.format("The correct answer is %B", true);
        assertEquals("The correct answer is TRUE", s);

        //Character Conversions
        s = String.format("The correct answer is %c", 'a');
        assertEquals("The correct answer is a", s);
        s = String.format("The correct answer is %c", null);
        assertEquals("The correct answer is null", s);
        s = String.format("The correct answer is %C", 'b');
        assertEquals("The correct answer is B", s);

        //Numeric Integral Conversions
        s = String.format("The number 25 in decimal = %d", 25);
        assertEquals("The number 25 in decimal = 25", s);
        s = String.format("The number 25 in octal = %o", 25);
        assertEquals("The number 25 in octal = 31", s);
        s = String.format("The number 25 in hexadecimal = %x", 25);
        assertEquals("The number 25 in hexadecimal = 19", s);

        //Numeric Floating-point Conversions
        s = String.format("The computerized scientific format of 10000.00 "
                + "= %e", 10000.00);
        assertEquals("The computerized scientific format of 10000.00 = 1.000000e+04", s);
        s = String.format("The decimal format of 10.019 = %f", 10.019);
        assertEquals("The decimal format of 10.019 = 10.019000", s);

        //Line Separator Conversion
        s = String.format("First Line %nSecond Line");
        assertEquals("First Line \n"
                + "Second Line", s);
    }

    @Test
    public void givenString_whenSpecifyFlag_thenGotFormattedString() {
        //Without left-justified flag
        String s = String.format("Without left justified flag: %5d", 25);
        assertEquals("Without left justified flag:    25", s);

        //Using left-justified flag
        s = String.format("With left justified flag: %-5d", 25);
        assertEquals("With left justified flag: 25   ", s);
    }

    @Test
    public void givenString_whenSpecifyPrecision_thenGotExpected() {

        //Precision
        String s = String.format("Output of 25.09878 with Precision 2: %.2f", 25.09878);
        assertEquals("Output of 25.09878 with Precision 2: 25.10", s);

        s = String.format("Output of general conversion type with Precision 2: %.2b", true);
        assertEquals("Output of general conversion type with Precision 2: tr", s);
    }

    @Test
    public void givenString_whenSpecifyArgumentIndex_thenGotExpected() {
        Calendar c = Calendar.getInstance();
        //Argument_Index
        String s = String.format("Today's date: %1$tm %1$te,%1$tY", c);
        assertEquals("Today's date: 11 7,2017", s);

        s = String.format("Today's date: %1$tm %<te,%<tY", c);
        assertEquals("Today's date: 11 7,2017", s);
    }

    @Test
    public void givenAppendable_whenCreateFormatter_thenFormatterWorksOnAppendable() {
        //Using String Formatter with Appendable
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format("I am writting to a %1$s Instance.", sb.getClass());
        assertEquals("I am writting to a class java.lang.StringBuilder Instance.", sb.toString());
    }
    
    @Test
    public void givenString_whenNoArguments_thenExpected() {
        //Using String Formatter without arguments
        String s = String.format("John scored 90%% in Fall semester");
        assertEquals("John scored 90% in Fall semester", s);
    }

}
