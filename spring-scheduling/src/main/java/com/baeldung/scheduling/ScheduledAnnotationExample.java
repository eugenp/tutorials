package com.baeldung.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("scheduledAnnotationExample")
public class ScheduledAnnotationExample {

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {
    }

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void scheduleFixedDelayTaskUsingExpression() {
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 2000)
    public void scheduleFixedDelayWithInitialDelayTask() {
    }

    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() {
    }

    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    public void scheduleFixedRateTaskUsingExpression() {
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void scheduleFixedRateWithInitialDelayTask() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("Fixed rate task with one second initial delay - " + now);
    }

    /**
     * Scheduled task is executed at 10:15 AM on the 15th day of every month
     */
    @Scheduled(cron = "0 15 10 15 * ?")
    public void scheduleTaskUsingCronExpression() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("schedule tasks using cron jobs - " + now);
    }

    @Scheduled(cron = "${cron.expression}")
    public void scheduleTaskUsingExternalizedCronExpression() {
    }

}
