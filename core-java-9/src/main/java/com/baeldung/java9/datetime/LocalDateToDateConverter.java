/**
 * 
 */
package com.baeldung.java9.datetime;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Class which shows different ways of converting java.time.LocalDate into java.util.Date. 
 *  
 * @author abialas
 *
 */
public class LocalDateToDateConverter {

    public static Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());
    }

}
