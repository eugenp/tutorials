package org.baeldung.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("scheduledAnnotationExample")
public class ScheduledAnnotationExample {

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {
        System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void scheduleFixedDelayTaskUsingExpression() {
        System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 2000)
    public void scheduleFixedDelayWithInitialDelayTask() {
        System.out.println("Fixed delay task with one second initial delay - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() {
        System.out.println("Fixed rate task - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    public void scheduleFixedRateTaskUsingExpression() {
        System.out.println("Fixed rate task - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 100)
    public void scheduleFixedRateWithInitialDelayTask() {
        System.out.println("Fixed delay task with one second initial delay - " + System.currentTimeMillis() / 1000);
    }

    /**
     * Scheduled task is executed at 10:15 AM on the 15th day of every month
     */
    @Scheduled(cron = "0 15 10 15 * ?")
    public void scheduleTaskUsingCronExpression() {
        System.out.println("schedule tasks using cron expressions - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(cron = "${cron.expression}")
    public void scheduleTaskUsingExternalizedCronExpression() {
        System.out.println("schedule tasks using externalized cron expressions - " + System.currentTimeMillis() / 1000);
    }

}
