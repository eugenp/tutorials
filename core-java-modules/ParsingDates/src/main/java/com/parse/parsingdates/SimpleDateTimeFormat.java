/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parse.parsingdates;

import java.util.Arrays;
import java.util.List;
import org.joda.time.format.DateTimeFormat;

public class SimpleDateTimeFormat {

    public boolean parseDate(String date) {
        List<String> patternList = Arrays.asList("MM/dd/yyyy", "dd.MM.yyyy", "yyyy-MM-dd");
        for (String pattern : patternList) {
            try {
                DateTimeFormat.forPattern(pattern).parseLocalDate(date);
                return true;
            } catch (Exception e) {
            }
        }
        return false;
    }

}
