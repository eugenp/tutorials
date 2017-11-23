/**
 * 
 */
package com.baeldung.java9.datetime;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Class which shows a way to convert java.util.Date into java.time.LocalDate with new Java 1.9.
 * 
 * @author abialas
 *
 */
public class DateToLocalDateConverter {

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return LocalDate.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
    }

}
