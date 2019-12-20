package com.baeldung.scheduling;

public class SchedulingWithXmlConfig {

    public void scheduleFixedDelayTask() {
        System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

    public void scheduleFixedRateTask() {
        System.out.println("Fixed rate task - " + System.currentTimeMillis() / 1000);
    }

    public void scheduleTaskUsingCronExpression() {
        System.out.println("schedule tasks using cron expressions - " + System.currentTimeMillis() / 1000);
    }
}
