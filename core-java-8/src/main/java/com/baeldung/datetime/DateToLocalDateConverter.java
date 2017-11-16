/**
 * 
 */
package com.baeldung.datetime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Class which shows different ways of converting java.util.Date into java.time.LocalDate.
 * 
 * @author abialas
 *
 */
public class DateToLocalDateConverter {

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

    public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public static LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return LocalDate.from(Instant.ofEpochMilli(dateToConvert.getTime())
            .atZone(ZoneId.systemDefault()));
    }

}
