package com.baeldung.cronfromdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.cronfromdb.crondata.CronConfigRepository;
import com.baeldung.cronfromdb.crondata.CronEntity;
import com.baeldung.cronfromdb.scheduling.DynamicScheduledConfig;

@RestController
public class CronController {

    private static final Logger log = LoggerFactory.getLogger(CronController.class);
    private final CronConfigRepository repository;

    public CronController(CronConfigRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cron")
    public String updateCron() {
        CronEntity cronEntity = repository.findById(1L)
            .orElseThrow(() -> new RuntimeException("Cron expression not found in database"));
        cronEntity.setCronExpression("*/10 * * * * ?");
        repository.save(cronEntity);
        String msg = "[DB] ⏰ Updated cron expression in DB to: */10 * * * * ?";
        log.info(msg);
        return msg;
    }
}