package com.baeldung.quartz;

import org.quartz.*;

import java.util.Date;

public class SimpleJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");

        System.out.println("Job says: " + jobSays + ", and val is: " + myFloatValue);

        // Access the Trigger to get the next fire time
        Trigger trigger = context.getTrigger();
        Date nextFireTime = trigger.getNextFireTime();
        System.out.println("Next execution: " + nextFireTime);
    }
}