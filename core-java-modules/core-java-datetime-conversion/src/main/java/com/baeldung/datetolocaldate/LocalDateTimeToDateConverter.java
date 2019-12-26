/**
 * 
 */
package com.baeldung.datetolocaldate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Class which shows different ways of converting java.time.LocalDateTime into java.util.Date. 
 *  
 * @author abialas
 *
 */
public class LocalDateTimeToDateConverter {

    public static Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault())
            .toInstant());
    }

}
