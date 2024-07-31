package com.baeldung.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzExample {

    public static void main(String args[]) {

        SchedulerFactory schedFact = new StdSchedulerFactory();
        try {

            Scheduler sched = schedFact.getScheduler();

            JobDetail job = JobBuilder.newJob(SimpleJob.class).withIdentity("myJob", "group1").usingJobData("jobSays", "Hello World!").usingJobData("myFloatValue", 3.141f).build();

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();

            JobDetail jobA = JobBuilder.newJob(JobA.class).withIdentity("jobA", "group2").build();

            JobDetail jobB = JobBuilder.newJob(JobB.class).withIdentity("jobB", "group2").build();

            Trigger triggerA = TriggerBuilder.newTrigger().withIdentity("triggerA", "group2").startNow().withPriority(15).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();

            Trigger triggerB = TriggerBuilder.newTrigger().withIdentity("triggerB", "group2").startNow().withPriority(10).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(20).repeatForever()).build();

            sched.scheduleJob(job, trigger);
            sched.scheduleJob(jobA, triggerA);
            sched.scheduleJob(jobB, triggerB);
            sched.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}