package org.baeldung.springquartz.scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.baeldung.springquartz.config.AutoWiringSpringBeanJobFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
@ConditionalOnExpression("'${using.spring.schedulerFactory}'=='false'")
public class QrtzScheduler {

    Logger _logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        _logger.info("Hello world from Quartz...");
    }

    @Bean(name = "SpringJobFactory")
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        _logger.debug("Configuring Job factory");

        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean(name = "Qrtz_Scheduler")
    public Scheduler scheduler(@Qualifier("Qrtz_Trigger") Trigger trigger,
            @Qualifier("Qrtz_Job_Detail") JobDetail job) throws SchedulerException, IOException {

        StdSchedulerFactory factory = new StdSchedulerFactory();
        factory.initialize(new ClassPathResource("quartz.properties").getInputStream());

        _logger.debug("Getting a handle to the Scheduler");
        Scheduler scheduler = factory.getScheduler();
        scheduler.setJobFactory(springBeanJobFactory());
        scheduler.scheduleJob(job, trigger);

        _logger.debug("Starting Scheduler threads");
        scheduler.start();
        return scheduler;
    }

    @Bean(name = "Qrtz_Job_Detail")
    public JobDetail jobDetail() {

        return newJob().ofType(SampleJob.class).storeDurably().withIdentity("Qrtz_Job_Detail")
                .withDescription("Invoke Sample Job service...").build();
    }

    @Bean(name = "Qrtz_Trigger")
    public Trigger trigger(@Qualifier("Qrtz_Job_Detail") JobDetail job) {

        int frequencyInSec = 10;
        _logger.info("Configuring trigger to fire every {} seconds", frequencyInSec);

        return newTrigger().forJob(job).withIdentity("Qrtz_Trigger")
                .withDescription("Sample trigger")
                .withSchedule(
                        simpleSchedule().withIntervalInSeconds(frequencyInSec).repeatForever())
                .build();
    }
}
