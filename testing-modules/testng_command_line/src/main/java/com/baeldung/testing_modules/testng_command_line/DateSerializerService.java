package com.baeldung.testing_modules.testng_command_line;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSerializerService {
    private SimpleDateFormat simpleDateFormat;

    public DateSerializerService() {
        super();
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
    }

    public String serializeDate(Date date) {
        return simpleDateFormat.format(date);
    }
}
