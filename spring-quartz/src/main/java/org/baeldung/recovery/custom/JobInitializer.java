package org.baeldung.recovery.custom;

import org.baeldung.recovery.config.SampleJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class JobInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ApplicationJobRepository jobRepository;

    @Autowired
    private Scheduler scheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (ApplicationJob job : jobRepository.findAll()) {
            if (job.isEnabled() && (job.getCompleted() == null || !job.getCompleted())) {
                JobDetail detail = JobBuilder.newJob(SampleJob.class)
                  .withIdentity(job.getName(), "appJobs")
                  .storeDurably()
                  .build();

                Trigger trigger = TriggerBuilder.newTrigger()
                  .forJob(detail)
                  .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(30)
                    .repeatForever())
                  .build();

                try {
                    scheduler.scheduleJob(detail, trigger);
                } catch (SchedulerException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
