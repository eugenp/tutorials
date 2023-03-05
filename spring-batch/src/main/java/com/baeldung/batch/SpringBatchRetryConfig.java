package com.baeldung.batch;

import com.baeldung.batch.model.Transaction;
import com.baeldung.batch.service.CustomItemProcessor;
import com.baeldung.batch.service.CustomSkipPolicy;
import com.baeldung.batch.service.MissingUsernameException;
import com.baeldung.batch.service.NegativeAmountException;
import com.baeldung.batch.service.RecordFieldSetMapper;
import com.baeldung.batch.service.RetryItemProcessor;
import com.baeldung.batch.service.SkippingItemProcessor;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

import java.net.http.HttpConnectTimeoutException;
import java.text.ParseException;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringBatchRetryConfig {
    
    private static final String[] tokens = { "username", "userid", "transactiondate", "amount" };
    private static final int TWO_SECONDS = 2000;

    @Value("org/springframework/batch/core/schema-drop-sqlite.sql")
    private Resource dropReopsitoryTables;

    @Value("org/springframework/batch/core/schema-sqlite.sql")
    private Resource dataReopsitorySchema;

    @Value("input/recordRetry.csv")
    private Resource inputCsv;

    @Value("file:xml/retryOutput.xml")
    private WritableResource outputXml;

    @Value("input/recordWithInvalidData.csv")
    private Resource invalidInputCsv;

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

    @Bean(name = "retryStep")
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
          .retry(HttpConnectTimeoutException.class)
          .retry(DeadlockLoserDataAccessException.class)
          .build();
    }

    @Bean(name = "retryBatchJob")
    public Job retryJob(JobRepository jobRepository, @Qualifier("retryStep") Step retryStep) {
        return new JobBuilder("retryBatchJob", jobRepository)
          .start(retryStep)
          .build();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:repository.sqlite");
        return dataSource;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();

        databasePopulator.addScript(dropReopsitoryTables);
        databasePopulator.addScript(dataReopsitorySchema);
        databasePopulator.setIgnoreFailedDrops(true);

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);

        return initializer;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean(name = "firstBatchJob")
    public Job job(JobRepository jobRepository, @Qualifier("step1") Step step1) {
        return new JobBuilder("firstBatchJob", jobRepository).start(step1).build();
    }

    @Bean(name = "step1")
    protected Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("itemProcessor") ItemProcessor<Transaction, Transaction> processor, ItemWriter<Transaction> writer) throws ParseException {
        return new StepBuilder("step1", jobRepository)
                .<Transaction, Transaction> chunk(10, transactionManager)
                .reader(itemReader(inputCsv))
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemProcessor<Transaction, Transaction> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean(name = "skippingBatchJob")
    public Job skippingJob(JobRepository jobRepository, @Qualifier("skippingStep") Step skippingStep) {
        return new JobBuilder("skippingBatchJob", jobRepository)
                .start(skippingStep)
                .build();
    }

    @Bean(name = "skippingStep")
    public Step skippingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("skippingItemProcessor") ItemProcessor<Transaction, Transaction> processor,
                             ItemWriter<Transaction> writer) {
        return new StepBuilder("", jobRepository)
                .<Transaction, Transaction>chunk(10, transactionManager)
                .reader(itemReader(invalidInputCsv))
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipLimit(2)
                .skip(MissingUsernameException.class)
                .skip(NegativeAmountException.class)
                .build();
    }

    @Bean
    public ItemProcessor<Transaction, Transaction> skippingItemProcessor() {
        return new SkippingItemProcessor();
    }

    @Bean(name = "skipPolicyBatchJob")
    public Job skipPolicyBatchJob(JobRepository jobRepository, @Qualifier("skipPolicyStep") Step skipPolicyStep) {
        return new JobBuilder("skipPolicyBatchJob", jobRepository)
                .start(skipPolicyStep)
                .build();
    }

    @Bean(name = "skipPolicyStep")
    public Step skipPolicyStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("skippingItemProcessor") ItemProcessor<Transaction, Transaction> processor,
                               ItemWriter<Transaction> writer) {
        return new StepBuilder("skipPolicyStep", jobRepository)
                .<Transaction, Transaction>chunk(10, transactionManager)
                .reader(itemReader(invalidInputCsv))
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipPolicy(new CustomSkipPolicy())
                .build();
    }

    @Bean(name = "jobRepository")
    public JobRepository getJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource());
        factory.setTransactionManager(getTransactionManager());
        // JobRepositoryFactoryBean's methods Throws Generic Exception,
        // it would have been better to have a specific one
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
