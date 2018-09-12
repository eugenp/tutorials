package com.baeldung.string;

import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class StringPerformanceTests {

    protected final int invocations_1000 = 1000;
    protected final int invocations_10_000 = 10000;
    protected final int invocations_100_000 = 100000;
    protected final int invocations_1_000_000 = 1000000;

    protected String baeldung = "baeldung";
    protected String longString = "Hello baeldung, I am a bit longer than other Strings";

    protected String dynamicConcat(int iterations) {
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += baeldung;
        }
        return result;
    }

    protected String StringConstructor(int iterations) {
        String result = null;
        for (int i = 0; i < iterations; i++) {
            result = new String(baeldung);
        }
        return result;
    }

    protected String StringLiteral(int iterations) {
        String result = null;
        for (int i = 0; i < iterations; i++) {
            result = baeldung;
        }
        return result;
    }

    protected String stringFormat_s(int iterations) {
        String result = null;
        for (int i = 0; i < iterations; i++) {
            result = String.format("hello %s, nice to meet you", baeldung);
        }
        return result;
    }

    protected String stringFormat_d(int iterations) {
        String result = null;
        for (int i = 0; i < iterations; i++) {
            result = String.format("%d", i);
        }
        return result;
    }

    protected String stringConcat(int iterations) {
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result = result.concat(baeldung);
        }
        return result;
    }

    protected List stringTokenizer(int iterations) {
        List<String> result = null;
        for (int i = 0; i < iterations; i++) {
            StringTokenizer st = new StringTokenizer(longString);
            List<String> list = new ArrayList<String>();
            while (st.hasMoreTokens()) {
                list.add(st.nextToken());
            }

            result = list;
        }
        return result;
    }

    protected List stringIndexOf(int iterations) {
        List<String> result = null;
        for (int i = 0; i < iterations; i++) {
            List<String> list = new ArrayList<>();
            int pos = 0, end;
            while ((end = longString.indexOf(' ', pos)) >= 0) {
                list.add(longString.substring(pos, end));
                pos = end + 1;
            }
            result = list;
        }
        return result;
    }

    protected String stringIntegerToString(int iterations) {
        String number = null;
        for (int i = 0; i < iterations; i++) {
            number = Integer.toString(i);
        }
        return number;
    }

    protected String stringValueOf(int iterations) {
        String number = null;
        for (int i = 0; i < iterations; i++) {
            number = String.valueOf(i);
        }
        return number;
    }


    protected String stringConvertPlus(int iterations) {
        String number = null;
        for (int i = 0; i < iterations; i++) {
            number = i + "";
        }
        return number;
    }


    protected boolean stringEquals(int iterations) {
        boolean isEqual = false;
        for (int i = 0; i < iterations; i++) {
            isEqual = longString.equals(baeldung);
        }
        return isEqual;
    }


    protected boolean stringEqualsIgnoreCase(int iterations) {
        boolean isEqual = false;
        for (int i = 0; i < iterations; i++) {
            isEqual = longString.equalsIgnoreCase(baeldung);
        }
        return isEqual;
    }

    protected boolean stringIsMatch(int iterations) {
        boolean isMatch = false;
        for (int i = 0; i < iterations; i++) {
            isMatch = longString.matches(baeldung);
        }
        return isMatch;
    }

    protected boolean precompiledMatches(int iterations) {
        boolean ismatch = false;
        Pattern pattern = Pattern.compile(longString);
        for (int i = 0; i < iterations; i++) {
            ismatch = pattern.matcher(baeldung).matches();
        }
        return ismatch;
    }


    protected int stringCompareTo(int iterations) {
        int result = 0;
        for (int i = 0; i < iterations; i++) {
            result = longString.compareTo(baeldung);
        }
        return result;
    }


    protected boolean stringIsEmpty(int iterations) {
        boolean result = false;
        for (int i = 0; i < iterations; i++) {
            result = longString.isEmpty();
        }
        return result;
    }


    protected boolean stringLengthZero(int iterations) {
        boolean result = false;
        for (int i = 0; i < iterations; i++) {
            result = longString.length() == 0;
        }
        return result;
    }

    protected String [] stringSplitPattern(int iterations) {
        String [] list = null;
        Pattern spacePattern = Pattern.compile(" ");
        for (int i = 0; i < iterations; i++) {
            list = spacePattern.split(longString, 0);
        }
        return list;
    }

    protected String [] stringSplit(int iterations) {
        String [] list = null;
        for (int i = 0; i < iterations; i++) {
            list = longString.split(" ");
        }
        return list;
    }

    protected List<String> guavaSplitter(int iterations) {
        List<String> result = null;
        for (int i = 0; i < iterations; i++) {
            result = Splitter.on(" ").trimResults()
                    .omitEmptyStrings()
                    .splitToList(longString);
        }

        return result;
    }

    protected String stringIntern(int iterations) {
        String number = null;
        for (int i = 0; i < iterations; i++) {
            number = Integer.toString( i ).intern();
        }

        return number;
    }
}
