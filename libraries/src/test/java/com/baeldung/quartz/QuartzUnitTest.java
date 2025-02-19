package com.baeldung.quartz;

import org.junit.jupiter.api.Test;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuartzUnitTest {

    @Test
    void testNextFireTime() throws ParseException {
        String cronExpression = "0 0/2 8-17 * * ?"; // Fires every 2 minutes between 8 AM and 5 PM
        CronExpression expression = new CronExpression(cronExpression);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date testTime = calendar.getTime(); // Set a fixed time: 10:00 AM

        Date nextFireTime = expression.getNextValidTimeAfter(testTime);

        assertNotNull(nextFireTime);

        // The next fire time should be at 10:02 AM
        calendar.add(Calendar.MINUTE, 2);
        Date expectedNextFireTime = calendar.getTime();

        assertEquals(expectedNextFireTime.getTime(), nextFireTime.getTime());
    }


}
