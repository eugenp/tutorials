package org.baeldung.conditionalflow.config;

import org.baeldung.conditionalflow.NumberInfoDecider;
import org.baeldung.conditionalflow.model.NumberInfo;
import org.baeldung.conditionalflow.step.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class NumberInfoConfig {

    @Bean
    @Qualifier("NotificationStep")
    public Step notificationStep(StepBuilderFactory sbf) {
        return sbf.get("Billing step").tasklet(new NotifierTasklet()).build();
    }

    public Step numberGeneratorStep(StepBuilderFactory sbf, int[] values, String prepend) {
        return sbf.get("Number generator")
                .<NumberInfo, Integer>chunk(1)
                .reader(new NumberInfoGenerator(values))
                .processor(new NumberInfoClassifier())
                .writer(new PrependingStdoutWriter<>(prepend))
                .build();
    }

    public Step numberGeneratorStepDecider(StepBuilderFactory sbf, int[] values, String prepend) {
        return sbf.get("Number generator")
                .<NumberInfo, Integer>chunk(1)
                .reader(new NumberInfoGenerator(values))
                .processor(new NumberInfoClassifierWithDecider())
                .writer(new PrependingStdoutWriter<>(prepend))
                .build();
    }

    @Bean
    public Job numberGeneratorNonNotifierJob(JobBuilderFactory jobBuilderFactory,
                                             StepBuilderFactory stepBuilderFactory,
                                             @Qualifier("NotificationStep") Step notificationStep
    ) {
        int[] nonNotifierData = {-1, -2, -3};
        Step step = numberGeneratorStep(stepBuilderFactory, nonNotifierData, "First Dataset Processor");
        return jobBuilderFactory.get("Number generator - first dataset")
                .start(step)
                .on("NOTIFY").to(notificationStep)
                .from(step).on("*").stop()
                .end()
                .build();
    }

    @Bean
    public Job numberGeneratorNotifierJob(JobBuilderFactory jobBuilderFactory,
                                          StepBuilderFactory stepBuilderFactory,
                                          @Qualifier("NotificationStep") Step notificationStep
    ) {
        int[] billableData = {11, -2, -3};
        Step dataProviderStep = numberGeneratorStep(stepBuilderFactory, billableData, "Second Dataset Processor");
        return jobBuilderFactory.get("Number generator - second dataset")
                .start(dataProviderStep)
                .on("NOTIFY").to(notificationStep)
                .end()
                .build();
    }

    @Bean
    public Job numberGeneratorNotifierJobWithDecider(JobBuilderFactory jobBuilderFactory,
                                                     StepBuilderFactory stepBuilderFactory,
                                                     @Qualifier("NotificationStep") Step notificationStep
    ) {
        int[] billableData = {11, -2, -3};
        Step dataProviderStep = numberGeneratorStepDecider(stepBuilderFactory, billableData, "Third Dataset Processor");
        return jobBuilderFactory.get("Number generator - third dataset")
                .start(dataProviderStep)
                .next(new NumberInfoDecider()).on("NOTIFY").to(notificationStep)
                .end()
                .build();
    }
}
