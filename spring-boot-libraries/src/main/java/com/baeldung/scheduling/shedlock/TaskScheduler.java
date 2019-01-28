package com.baeldung.scheduling.shedlock;

import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class TaskScheduler {

    @Scheduled(cron = "*/15 * * * *")
    @SchedulerLock(name = "TaskScheduler_scheduledTask", lockAtLeastForString = "PT5M", lockAtMostForString = "PT14M")
    public void scheduledTask() {
        System.out.println("Running ShedLock task");
    }
}
