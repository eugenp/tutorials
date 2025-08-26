package org.baeldung.recovery.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder.newJob(SampleJob.class)
          .withIdentity("sampleJob", "group1")
          .storeDurably()
          .requestRecovery(true)
          .build();
    }

    @Bean
    public Trigger sampleTrigger(JobDetail sampleJobDetail) {
        return TriggerBuilder.newTrigger()
          .forJob(sampleJobDetail)
          .withIdentity("sampleTrigger", "group1")
          .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?")) // every 30s
          .build();
    }
}
