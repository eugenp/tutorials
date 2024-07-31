package com.baeldung.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobB implements Job {

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("This is the job B");
    }

}