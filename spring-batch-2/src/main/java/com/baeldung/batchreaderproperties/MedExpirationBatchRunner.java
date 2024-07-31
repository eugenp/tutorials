package com.baeldung.batchreaderproperties;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class MedExpirationBatchRunner {

    @Autowired
    private Job medExpirationJob;

    @Autowired
    private JobLauncher jobLauncher;

    @Value("${batch.medicine.alert_type}")
    private String alertType;

    @Value("${batch.medicine.expiration.default.days}")
    private long defaultExpiration;

    @Value("${batch.medicine.start.sale.default.days}")
    private long saleStartDays;

    @Value("${batch.medicine.sale}")
    private double medicineSale;

    @Scheduled(cron = "${batch.medicine.cron}", zone = "GMT")
    public void runJob() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        launchJob(now);
    }

    public void launchJob(ZonedDateTime triggerZonedDateTime) {
        try {
            JobParameters jobParameters = new JobParametersBuilder().addString(BatchConstants.TRIGGERED_DATE_TIME, triggerZonedDateTime.toString())
                .addString(BatchConstants.ALERT_TYPE, alertType)
                .addLong(BatchConstants.DEFAULT_EXPIRATION, defaultExpiration)
                .addLong(BatchConstants.SALE_STARTS_DAYS, saleStartDays)
                .addDouble(BatchConstants.MEDICINE_SALE, medicineSale)
                .addString(BatchConstants.TRACE_ID, UUID.randomUUID()
                    .toString())
                .toJobParameters();

            jobLauncher.run(medExpirationJob, jobParameters);
        } catch (Exception e) {
            log.error("Failed to run", e);
        }
    }

}
