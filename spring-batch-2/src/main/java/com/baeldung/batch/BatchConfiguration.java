package com.baeldung.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {
    
    @Value("${file.input}")
    private String fileInput;

    @Bean
    public FlatFileItemReader<Coffee> reader() {
        return new FlatFileItemReaderBuilder<Coffee>().name("coffeeItemReader")
            .resource(new ClassPathResource(fileInput))
            .delimited()
            .names(new String[] { "brand", "origin", "characteristics" })
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Coffee>() {{
                setTargetType(Coffee.class);
             }})
            .build();
    }

    @Bean
    public CoffeeItemProcessor processor() {
        return new CoffeeItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Coffee> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Coffee>().itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO coffee (brand, origin, characteristics) VALUES (:brand, :origin, :characteristics)")
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Coffee> writer) {
        return new StepBuilder("step1", jobRepository)
            .<Coffee, Coffee> chunk(10, transactionManager)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }

}
