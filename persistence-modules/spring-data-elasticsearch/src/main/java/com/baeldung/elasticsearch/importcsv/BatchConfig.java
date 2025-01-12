package com.baeldung.elasticsearch.importcsv;

import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.RestClients;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Bean
    public FlatFileItemReader<Product> reader() {
        return new FlatFileItemReaderBuilder<Product>().name("productReader")
            .resource(new FileSystemResource("products.csv"))
            .delimited()
            .names("id", "name", "category", "price", "stock")
            .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(Product.class);
            }})
            .build();
    }

    @Bean
    public ItemWriter<Product> writer(RestHighLevelClient restHighLevelClient) {
        return products -> {
            for (Product product : products) {
                try {
                    IndexRequest request = new IndexRequest("products").id(product.getId())
                        .source(Map.of("name", product.getName(), "category", product.getCategory(), "price", product.getPrice(), "stock", product.getStock()));
                    restHighLevelClient.index(request, RequestOptions.DEFAULT);
                } catch (Exception e) {
                    System.err.println("Failed to index product: " + product.getId() + ", error: " + e.getMessage());
                }
            }
        };
    }

    @Bean
    public Job importJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, RestHighLevelClient restHighLevelClient) {
        return new JobBuilder("importJob", jobRepository)
            .start(new StepBuilder("importStep", jobRepository)
                .<Product, Product>chunk(10, transactionManager)
                .reader(reader())
                .writer(writer(restHighLevelClient))
                .build())
            .build();
    }
}
