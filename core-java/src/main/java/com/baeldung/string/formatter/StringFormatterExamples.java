package com.baeldung.string.formatter;

import java.util.Calendar;
import java.util.Formatter;

public class StringFormatterExamples {

    public static void main(String[] args) {

        //Syntax of Format Specifiers for Date/Time Representation
        Calendar c = Calendar.getInstance();
        String s = String.format("Today's date: %1$tm %1$te,%1$tY", c);
        System.out.println(s);

        //General Conversions
        System.out.println(String.format("The correct answer is %s", false));
        System.out.println(String.format("The correct answer is %b", null));
        System.out.println(String.format("The correct answer is %B", true));

        //Character Conversions
        System.out.println(String.format("The correct answer is %c", 'a'));
        System.out.println(String.format("The correct answer is %c", null));
        System.out.println(String.format("The correct answer is %C", 'b'));

        //Numeric Integral Conversions
        System.out.println(String.format("The number 25 in decimal = %d", 25));
        System.out.println(String.format("The number 25 in octal = %o", 25));
        System.out.println(String.format("The number 25 in hexadecimal = %x", 25));

        //Numeric Floating-point Conversions
        System.out.println(String.format("The computerized scientific format of 10000.00 "
                + "= %e", 10000.00));
        System.out.println(String.format("The decimal format of 10.019 = %f", 10.019));

        //Line Separator Conversion
        System.out.println(String.format("First Line %nSecond Line"));

        //Without left-justified flag
        System.out.println(String.format("Without left justified flag: %5d", 25));

        //Using left-justified flag
        System.out.println(String.format("With left justified flag: %-5d", 25));

        //Precision
        System.out.println(String.format("Output of 25.09878 with Precision 2: %.2f", 25.09878));
        System.out.println(String.format("Output of general conversion type with Precision 2: %.2b", true));

        //Argument_Index
        String.format("Today's date: %1$tm %1$te,%1$tY", c);
        String.format("Today's date: %1$tm %<te,%<tY", c);

        //Using String Formatter with Appendable
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format("I am writting to a %1$s Instance.", sb.getClass());
        System.out.println(sb.toString());
    }
}
