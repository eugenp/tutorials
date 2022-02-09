package com.baeldung.testing_modules.testng_command_line;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSerializerService {
    public String serializeDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
