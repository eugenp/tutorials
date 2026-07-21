package com.baeldung.cronfromdb.scheduling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.cronfromdb.crondata.CronConfigRepository;
import com.baeldung.cronfromdb.crondata.CronEntity;

@Configuration
public class CronLoaderConfig {

    @Bean
    String cronLoader(CronConfigRepository repository) {
        return repository.findById(1L)
            .map(CronEntity::getCronExpression)
            .orElseThrow(() -> new RuntimeException("Cron expression not found in DB"));
    }

}