package org.baeldung.recovery.config;

import java.util.List;
import java.util.Set;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class JobInitializer implements ApplicationListener<ContextRefreshedEvent> {

    Logger logger = LoggerFactory.getLogger(JobInitializer.class);

//    @Autowired
//    DataMiningJobRepository repository;

//    @Autowired
//    ApplicationJobRepository jobRepository;

    @Autowired
    Scheduler scheduler;

//    @Autowired
//    JobSchedulerLocator locator;

    @Autowired
    ListableBeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Job Initilizer started.");

        //TODO: Modify this call to only pull completed & enabled jobs
        /*for (ApplicationJob applicationJob : jobRepository.findAll()) {
            if (applicationJob.getIsEnabled() && (applicationJob.getIsCompleted() == null || !applicationJob.getIsCompleted())) {
                JobSchedulerUtil.schedule(new JobContext(beanFactory, scheduler, locator, applicationJob));
            }
        }*/

//        public void listJobs() throws SchedulerException {
        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
                for (JobKey jobKey : jobKeys) {
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);

                    System.out.println("Job: " + jobKey.getName() + ", Group: " + groupName);
                    for (Trigger trigger : triggers) {
                        System.out.println("  Trigger: " + trigger.getKey() + ", Next Fire: " + trigger.getNextFireTime());
                    }
                }
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        //        }
    }
}
