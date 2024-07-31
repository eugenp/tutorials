package com.baeldung.monitoring.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MonitoringUtil {
    public static final Date startTime = new Date();
    public static final Date endTime = new Date(startTime.getTime() + 30 * 1000);

    public static String time() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        return date;
    }
}
