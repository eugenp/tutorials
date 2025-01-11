package com.baeldung.springbatch.jobs.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import static org.quartz.JobBuilder.newJob;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

@Configuration
public class QuartzConfig {

    @Autowired
    private Job quartzJobOne;

    @Autowired
    private Job quartzJobTwo;

    @Bean
    public JobDetail job1Detail() {
        return JobBuilder.newJob()
            .ofType(quartzJobOne.getClass())
            .withIdentity("quartzJobOne", "group1")
            .storeDurably()
            .build();
    }

    @Bean
    public JobDetail job2Detail() {
        return JobBuilder.newJob()
            .ofType(quartzJobTwo.getClass())
            .withIdentity("quartzJobTwo", "group1")
            .storeDurably()
            .build();
    }

    @Bean
    public Trigger job1Trigger(JobDetail job1Detail) {
        return TriggerBuilder.newTrigger()
            .forJob(job1Detail)
            .withIdentity("quartzJobOneTrigger", "group1")
            .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
            .build();
    }

    @Bean
    public Trigger job2Trigger(JobDetail job2Detail) {
        return TriggerBuilder.newTrigger()
            .forJob(job2Detail)
            .withIdentity("quartzJobTwoTrigger", "group1")
            .withSchedule(CronScheduleBuilder.cronSchedule("0/15 * * * * ?"))
            .build();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobDetails(job1Detail(), job2Detail());
        schedulerFactory.setTriggers(job1Trigger(job1Detail()), job2Trigger(job2Detail()));
        return schedulerFactory;
    }

}
