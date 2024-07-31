package com.baeldung.scheduling.dynamic;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@ComponentScan("com.baeldung.scheduling.dynamic")
@EnableScheduling
public class DynamicSchedulingConfig implements SchedulingConfigurer {

    @Autowired
    private TickService tickService;

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
          () -> tickService.tick(),
          context -> {
              Optional<Date> lastCompletionTime =
                Optional.ofNullable(context.lastCompletionTime());
              Instant nextExecutionTime =
                lastCompletionTime.orElseGet(Date::new).toInstant()
                  .plusMillis(tickService.getDelay());
              return Date.from(nextExecutionTime).toInstant();
          }
        );
    }

}
