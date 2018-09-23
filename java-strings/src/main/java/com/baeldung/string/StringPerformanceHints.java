package com.baeldung.string;

import com.google.common.base.Splitter;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

@State(Scope.Thread)
public class StringPerformanceHints {

    protected String baeldung = "baeldung";
    protected String longString = "Hello baeldung, I am a bit longer than other Strings";
    protected String formatString = "hello %s, nice to meet you";
    protected String formatDigit = "%d";
    protected String emptyString = " ";
    protected String result = "";

    protected int sampleNumber = 100;

    protected Pattern spacePattern = Pattern.compile(emptyString);
    protected Pattern longPattern = Pattern.compile(longString);
    protected List<String> stringSplit = new ArrayList<>();
    protected List<String> stringTokenizer = new ArrayList<>();

    protected String dynamicConcat() {
        result += baeldung;
        return result;
    }

    protected String stringFormat_s() {
        return String.format(formatString, baeldung);
    }

    protected String stringFormat_d() {
        return String.format(formatDigit, sampleNumber);
    }

    protected String stringConcat() {
        result = result.concat(baeldung);
        return result;
    }

    protected List stringTokenizer() {
        StringTokenizer st = new StringTokenizer(longString);
        while (st.hasMoreTokens()) {
            stringTokenizer.add(st.nextToken());
        }
        return stringTokenizer;
    }

    protected List stringIndexOf() {
        int pos = 0, end;
        while ((end = longString.indexOf(' ', pos)) >= 0) {
            stringSplit.add(longString.substring(pos, end));
            pos = end + 1;
        }
        return stringSplit;
    }

    protected String stringIntegerToString() {
        return Integer.toString(sampleNumber);
    }

    protected String stringValueOf() {
        return String.valueOf(sampleNumber);
    }


    protected String stringConvertPlus() {
        return sampleNumber + "";
    }


    protected boolean stringEquals() {
        return longString.equals(baeldung);
    }


    protected boolean stringEqualsIgnoreCase() {
        return longString.equalsIgnoreCase(baeldung);
    }

    protected boolean stringIsMatch() {
        return longString.matches(baeldung);
    }

    protected boolean precompiledMatches() {
        return longPattern.matcher(baeldung).matches();
    }

    protected int stringCompareTo() {
        return longString.compareTo(baeldung);
    }

    protected boolean stringIsEmpty() {
        return longString.isEmpty();
    }

    protected boolean stringLengthZero() {
        return longString.length() == 0;
    }

    protected String [] stringSplitPattern() {
        return spacePattern.split(longString, 0);
    }

    protected String [] stringSplit() {
        return longString.split(emptyString);
    }

    protected List<String> guavaSplitter() {
        return Splitter.on(" ").trimResults()
                .omitEmptyStrings()
                .splitToList(longString);
    }

    protected String stringIntern() {
        return baeldung.intern();
    }
}
