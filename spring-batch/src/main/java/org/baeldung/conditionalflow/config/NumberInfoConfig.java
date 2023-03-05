package org.baeldung.conditionalflow.config;

import org.baeldung.conditionalflow.NumberInfoDecider;
import org.baeldung.conditionalflow.model.NumberInfo;
import org.baeldung.conditionalflow.step.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import static org.baeldung.conditionalflow.NumberInfoDecider.NOTIFY;

@Configuration
@EnableBatchProcessing
public class NumberInfoConfig {

    @Bean
    @Qualifier("NotificationStep")
    public Step notificationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Notify step", jobRepository)
            .tasklet(new NotifierTasklet(), transactionManager)
            .build();
    }

    public Step numberGeneratorStep(JobRepository jobRepositories, PlatformTransactionManager transactionManager, int[] values, String prepend) {
        return new StepBuilder("Number generator", jobRepositories)
            .<NumberInfo, Integer> chunk(1, transactionManager)
            .reader(new NumberInfoGenerator(values))
            .processor(new NumberInfoClassifier())
            .writer(new PrependingStdoutWriter<>(prepend))
            .build();
    }

    public Step numberGeneratorStepDecider(JobRepository jobRepositories, PlatformTransactionManager transactionManager, int[] values, String prepend) {
        return new StepBuilder("Number generator decider", jobRepositories)
            .<NumberInfo, Integer> chunk(1, transactionManager)
            .reader(new NumberInfoGenerator(values))
            .processor(new NumberInfoClassifierWithDecider())
            .writer(new PrependingStdoutWriter<>(prepend))
            .build();
    }

    @Bean
    @Qualifier("first_job")
    public Job numberGeneratorNonNotifierJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("NotificationStep") Step notificationStep) {
        int[] nonNotifierData = { -1, -2, -3 };
        Step step = numberGeneratorStep(jobRepository, transactionManager, nonNotifierData, "First Dataset Processor");
        return new JobBuilder("Number generator - first dataset", jobRepository)
            .start(step)
            .on(NOTIFY)
            .to(notificationStep)
            .from(step)
            .on("*")
            .stop()
            .end()
            .build();
    }

    @Bean
    @Qualifier("second_job")
    public Job numberGeneratorNotifierJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("NotificationStep") Step notificationStep) {
        int[] billableData = { 11, -2, -3 };
        Step dataProviderStep = numberGeneratorStep(jobRepository, transactionManager, billableData, "Second Dataset Processor");
        return new JobBuilder("Number generator - second dataset", jobRepository)
            .start(dataProviderStep)
            .on(NOTIFY)
            .to(notificationStep)
            .end()
            .build();
    }

    @Bean
    @Qualifier("third_job")
    @Primary
    public Job numberGeneratorNotifierJobWithDecider(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("NotificationStep") Step notificationStep) {
        int[] billableData = { 11, -2, -3 };
        Step dataProviderStep = numberGeneratorStepDecider(jobRepository, transactionManager, billableData, "Third Dataset Processor");
        return new JobBuilder("Number generator - third dataset", jobRepository)
            .start(dataProviderStep)
            .next(new NumberInfoDecider())
            .on(NOTIFY)
            .to(notificationStep)
            .end()
            .build();
    }
}
