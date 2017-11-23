/**
 * 
 */
package com.baeldung.java9.datetime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Class which shows a way to convert java.util.Date into java.time.LocalDateTime with new Java 1.9.
 * 
 * @author abialas
 *
 */
public class DateToLocalDateTimeConverter {

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return LocalDateTime.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
    }

}
