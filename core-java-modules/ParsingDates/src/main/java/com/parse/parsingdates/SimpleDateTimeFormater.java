/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parse.parsingdates;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class SimpleDateTimeFormater {

    public boolean parseDate(String date) {
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofPattern("[MM/dd/yyyy]" + "[dd-MM-yyyy]" + "[yyyy-MM-dd]"));

        DateTimeFormatter dateTimeFormatter = dateTimeFormatterBuilder.toFormatter();

        try {
            dateTimeFormatter.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
