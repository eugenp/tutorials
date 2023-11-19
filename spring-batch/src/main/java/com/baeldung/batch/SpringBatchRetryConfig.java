package com.baeldung.batch;

import com.baeldung.batch.model.Transaction;
import com.baeldung.batch.service.RecordFieldSetMapper;
import com.baeldung.batch.service.RetryItemProcessor;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchRetryConfig {

    private static final String[] tokens = { "username", "userid", "transactiondate", "amount" };
    private static final int TWO_SECONDS = 2000;

    @Value("input/recordRetry.csv")
    private Resource inputCsv;

    @Value("file:xml/retryOutput.xml")
    private WritableResource outputXml;

    public ItemReader<Transaction> itemReader(Resource inputData) {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(tokens);
        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<>();
        reader.setResource(inputData);
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public CloseableHttpClient closeableHttpClient() {
        final RequestConfig config = RequestConfig.custom()
          .setConnectTimeout(TWO_SECONDS)
          .build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    @Bean
    public ItemProcessor<Transaction, Transaction> retryItemProcessor() {
        return new RetryItemProcessor();
    }

    @Bean
    public ItemWriter<Transaction> itemWriter(Marshaller marshaller) {
        StaxEventItemWriter<Transaction> itemWriter = new StaxEventItemWriter<>();
        itemWriter.setMarshaller(marshaller);
        itemWriter.setRootTagName("transactionRecord");
        itemWriter.setResource(outputXml);
        return itemWriter;
    }

    @Bean
    public Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Transaction.class);
        return marshaller;
    }

    @Bean
    public Step retryStep(
            JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("retryItemProcessor") ItemProcessor<Transaction, Transaction> processor,
            ItemWriter<Transaction> writer) {
        return new StepBuilder("retryStep", jobRepository)
          .<Transaction, Transaction>chunk(10, transactionManager)
          .reader(itemReader(inputCsv))
          .processor(processor)
          .writer(writer)
          .faultTolerant()
          .retryLimit(3)
          .retry(ConnectTimeoutException.class)
          .retry(DeadlockLoserDataAccessException.class)
          .build();
    }

    @Bean(name = "retryBatchJob")
    public Job retryJob(JobRepository jobRepository, @Qualifier("retryStep") Step retryStep) {
        return new JobBuilder("retryBatchJob", jobRepository)
          .start(retryStep)
          .build();
    }
}
