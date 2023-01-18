/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parse.parsingdates;

import java.text.ParseException;
import org.apache.commons.lang3.time.DateUtils;

public class SimpleDateUtils {

    public boolean parseDate(String date) {
        try {
            DateUtils.parseDateStrictly(date,
                    new String[]{"yyyy/MM/dd", "dd/MM/yyyy", "yyyy-MM-dd"});
            return true;
        } catch (ParseException ex) {
        }
        return false;
    }

}
