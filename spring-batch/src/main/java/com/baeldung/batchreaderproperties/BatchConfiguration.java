package com.baeldung.batchreaderproperties;

import java.time.ZonedDateTime;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.baeldung.batchreaderproperties.job.ExpiresSoonMedicineReader;
import com.baeldung.batchreaderproperties.job.MedicineProcessor;
import com.baeldung.batchreaderproperties.job.MedicineWriter;
import com.baeldung.batchreaderproperties.model.Medicine;

@Configuration
public class BatchConfiguration {

    @Bean
    @StepScope
    public ExpiresSoonMedicineReader expiresSoonMedicineReader(JdbcTemplate jdbcTemplate, @Value("#{jobParameters}") Map<String, Object> jobParameters) {

        ExpiresSoonMedicineReader medicineReader = new ExpiresSoonMedicineReader(jdbcTemplate);
        enrichWithJobParameters(jobParameters, medicineReader);
        return medicineReader;
    }

    @Bean
    @StepScope
    public MedicineProcessor medicineProcessor(@Value("#{jobParameters}") Map<String, Object> jobParameters) {
        MedicineProcessor medicineProcessor = new MedicineProcessor();
        enrichWithJobParameters(jobParameters, medicineProcessor);
        return medicineProcessor;
    }

    @Bean
    @StepScope
    public MedicineWriter medicineWriter(@Value("#{jobParameters}") Map<String, Object> jobParameters) {
        MedicineWriter medicineWriter = new MedicineWriter();
        enrichWithJobParameters(jobParameters, medicineWriter);
        return medicineWriter;
    }

    @Bean
    public Job medExpirationJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, MedicineWriter medicineWriter, MedicineProcessor medicineProcessor, ExpiresSoonMedicineReader expiresSoonMedicineReader) {
        Step notifyAboutExpiringMedicine = new StepBuilder("notifyAboutExpiringMedicine", jobRepository).<Medicine, Medicine>chunk(10)
            .reader(expiresSoonMedicineReader)
            .processor(medicineProcessor)
            .writer(medicineWriter)
            .faultTolerant()
            .transactionManager(transactionManager)
            .build();

        return new JobBuilder("medExpirationJob", jobRepository).incrementer(new RunIdIncrementer())
            .start(notifyAboutExpiringMedicine)
            .build();
    }

    private void enrichWithJobParameters(Map<String, Object> jobParameters, ContainsJobParameters container) {
        if (jobParameters.get(BatchConstants.TRIGGERED_DATE_TIME) != null) {
            container.setTriggeredDateTime(ZonedDateTime.parse(jobParameters.get(BatchConstants.TRIGGERED_DATE_TIME)
                .toString()));
        }
        if (jobParameters.get(BatchConstants.TRACE_ID) != null) {
            container.setTraceId(jobParameters.get(BatchConstants.TRACE_ID)
                .toString());
        }
    }

}
