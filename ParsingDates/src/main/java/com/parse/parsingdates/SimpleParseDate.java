/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parse.parsingdates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


public class SimpleParseDate {

    private static Pattern DATE_PATTERNYYYYMMDD = Pattern.compile(
            "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$"
            + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
    private static Pattern DATE_PATTERNDDMMYYYY = Pattern.compile(
            "^(29-02-(2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26])))$"
            + "|^((0[1-9]|1[0-9]|2[0-8])-02-((19|2[0-9])[0-9]{2}))$"
            + "|^((0[1-9]|[12][0-9]|3[01])-(0[13578]|10|12)-((19|2[0-9])[0-9]{2}))$"
            + "|^((0[1-9]|[12][0-9]|30)-(0[469]|11)-((19|2[0-9])[0-9]{2}))$");
    private static Pattern DATE_PATTERNMMDDYYYY = Pattern.compile(
            "^(29/02/(2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26])))$"
            + "|^((0[1-9]|1[0-9]|2[0-8])/02/((19|2[0-9])[0-9]{2}))$"
            + "|^((0[1-9]|[12][0-9]|3[01])/(0[13578]|10|12)/((19|2[0-9])[0-9]{2}))$"
            + "|^((0[1-9]|[12][0-9]|30)/(0[469]|11)/((19|2[0-9])[0-9]{2}))$");

    public SimpleParseDate() {
    }

    public Date parseDateWithMatcher(String date) {

        if (matches(DATE_PATTERNYYYYMMDD, date)) {
            return parseDate(date, Arrays.asList("yyyy-MM-dd"));
        } else if (matches(DATE_PATTERNDDMMYYYY, date)) {
            return parseDate(date, Arrays.asList("dd-MM-yyyy"));
        } else if (matches(DATE_PATTERNMMDDYYYY, date)) {
            return parseDate(date, Arrays.asList("MM/dd/yyyy"));
        }

        return null;
    }

    public static boolean matches(Pattern patern, String date) {
        return patern.matcher(date).matches();
    }

    public static Date parseDate(String dateString, List<String> formatStrings) {
        for (String formatString : formatStrings) {
            try {
                return new SimpleDateFormat(formatString).parse(dateString);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    public static Pattern getDATE_PATTERNYYYYMMDD() {
        return DATE_PATTERNYYYYMMDD;
    }

    public static Pattern getDATE_PATTERNDDMMYYYY() {
        return DATE_PATTERNDDMMYYYY;
    }

    public static Pattern getDATE_PATTERNMMDDYYYY() {
        return DATE_PATTERNMMDDYYYY;
    }

}
