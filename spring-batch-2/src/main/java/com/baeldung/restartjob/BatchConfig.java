package com.baeldung.restartjob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Bean
    public Job simpleJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("simpleJob", jobRepository)
          .start(step1(jobRepository, transactionManager))
          .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
          .<String, String>chunk(2, transactionManager)
          .reader(flatFileItemReader())
          .processor(itemProcessor())
          .writer(itemWriter())
          .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<String> flatFileItemReader() {
        return new FlatFileItemReaderBuilder<String>()
          .name("itemReader")
          .resource(new ClassPathResource("data.csv"))
          .lineMapper(new PassThroughLineMapper())
          .saveState(true)
          .build();
    }

    @Bean
    public ItemProcessor<String, String> itemProcessor() {
        return item -> {
          System.out.println("Processing: " + item);

          if (item.equals("Item3")) {
              throw new RuntimeException("Simulated failure on Item3");
          }

          return "PROCESSED " + item;
        };
    }

    @Bean
    public ItemWriter<String> itemWriter() {
        return items -> {
          System.out.println("Writing items:");
          for (String item : items) {
              System.out.println("- " + item);
          }
        };
    }
}