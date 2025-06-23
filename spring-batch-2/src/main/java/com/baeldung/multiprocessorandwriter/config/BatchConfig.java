package com.baeldung.multiprocessorandwriter.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.baeldung.multiprocessorandwriter.model.Customer;
import com.baeldung.multiprocessorandwriter.processor.CustomerProcessorRouter;
import com.baeldung.multiprocessorandwriter.processor.TypeAProcessor;
import com.baeldung.multiprocessorandwriter.processor.TypeBProcessor;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public FlatFileItemReader<Customer> customerReader() {
        return new FlatFileItemReaderBuilder<Customer>().name("customerItemReader")
            .resource(new ClassPathResource("customers.csv"))
            .delimited()
            .names("id", "name", "email", "type")
            .linesToSkip(1)
            .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(Customer.class);
            }})
            .build();
    }

    @Bean
    public TypeAProcessor typeAProcessor() {
        return new TypeAProcessor();
    }

    @Bean
    public TypeBProcessor typeBProcessor() {
        return new TypeBProcessor();
    }

    @Bean
    public CustomerProcessorRouter processorRouter(TypeAProcessor typeAProcessor, TypeBProcessor typeBProcessor) {
        return new CustomerProcessorRouter(typeAProcessor, typeBProcessor);
    }

    @Bean
    public JpaItemWriter<Customer> jpaDBWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Customer> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public FlatFileItemWriter<Customer> fileWriter() {
        return new FlatFileItemWriterBuilder<Customer>().name("customerItemWriter")
            .resource(new FileSystemResource("output/processed_customers.txt"))
            .delimited()
            .delimiter(",")
            .names("id", "name", "email", "type")
            .build();
    }

    @Bean
    public CompositeItemWriter<Customer> compositeWriter(JpaItemWriter<Customer> jpaDBWriter, FlatFileItemWriter<Customer> fileWriter) {
        return new CompositeItemWriterBuilder<Customer>().delegates(List.of(jpaDBWriter, fileWriter))
            .build();
    }

    @Bean
    public Step processCustomersStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, FlatFileItemReader<Customer> reader,
        CustomerProcessorRouter processorRouter, CompositeItemWriter<Customer> compositeWriter) {
        return new StepBuilder("processCustomersStep", jobRepository).<Customer, Customer> chunk(10, transactionManager)
            .reader(reader)
            .processor(processorRouter)
            .writer(compositeWriter)
            .build();
    }

    @Bean
    public Job processCustomersJob(JobRepository jobRepository, Step processCustomersStep) {
        return new JobBuilder("customerProcessingJob", jobRepository).start(processCustomersStep)
            .build();
    }
}
