package com.baeldung.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;

import com.baeldung.batch.model.Product;

@Configuration
@EnableBatchProcessing
public class CompositeItemReaderConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .driverClassName("org.h2.Driver")
            .url("jdbc:h2:mem:batchdb;DB_CLOSE_DELAY=-1;")
            .username("sa")
            .password("")
            .build();
    }


    @Bean
    public FlatFileItemReader<Product> fileReader() {
        return new FlatFileItemReaderBuilder<Product>()
            .name("fileReader")
            .resource(new ClassPathResource("products.csv"))
            .delimited()
            .names("productId", "productName", "stock", "price")
            .linesToSkip(1)
            .targetType(Product.class)
            .build();
    }

    @Bean
    public JdbcCursorItemReader<Product> dbReader() {
        return new JdbcCursorItemReaderBuilder<Product>()
            .name("dbReader")
            .dataSource(dataSource())
            .sql("SELECT productid, productname, stock, price FROM products")
            .rowMapper((rs, rowNum) -> new Product(
                rs.getLong("productid"),
                rs.getString("productname"),
                rs.getInt("stock"),
                rs.getBigDecimal("price")
            ))
            .build();
    }

    @Bean
    public CompositeItemReader<Product> compositeReader() {
        return new CompositeItemReader<>(Arrays.asList(fileReader(), dbReader()));
    }

    @Bean
    public ItemWriter<Product> productWriter() {
        return items -> {
            for (Product product : items) {
                System.out.println("Writing product: " + product);
            }
        };
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("compositeReader")  ItemReader<Product> compositeReader, ItemWriter<Product> productWriter) {
        return new StepBuilder("productStep", jobRepository)
            .<Product, Product>chunk(10, transactionManager)
            .reader(compositeReader)
            .writer(productWriter)
            .build();
    }

    @Bean
    public Job productJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("productJob", jobRepository)
            .start(step)
            .build();
    }
}