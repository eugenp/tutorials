package com.baeldung.blade.sample.configuration;

import com.blade.ioc.annotation.Bean;
import com.blade.task.annotation.Schedule;

@Bean
public class ScheduleExample {
 
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ScheduleExample.class);
    
    @Schedule(name = "baeldungTask", cron = "0 */1 * * * ?")
    public void runScheduledTask() {
        log.info("[ScheduleExample] This is a scheduled Task running once per minute.");
    }
}
