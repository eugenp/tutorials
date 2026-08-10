package com.baeldung.cronfromdb.scheduling;

import static java.time.LocalTime.now;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import com.baeldung.cronfromdb.crondata.CronConfigRepository;
import com.baeldung.cronfromdb.crondata.CronEntity;

@Configuration
public class DynamicScheduledConfig implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(DynamicScheduledConfig.class);

    private final CronConfigRepository repository;

    public DynamicScheduledConfig(CronConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> log.info("✅ [{}] DynamicScheduledConfig executed - cron re-read from DB per execution", now()), triggerContext -> {
            String cronExpression = repository.findById(1L)
                .map(CronEntity::getCronExpression)
                .orElseThrow(() -> new RuntimeException("Cron expression not found in DB"));
            return new CronTrigger(cronExpression).nextExecution(triggerContext);
        });
    }
}