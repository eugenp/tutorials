package com.baeldung.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UseSimpleDateFormat {

    public String useFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        Date date = new Date(1725437542000L);
        return sdf.format(date);
    }
}
