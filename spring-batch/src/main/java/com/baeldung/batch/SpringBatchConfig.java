package com.baeldung.batch;

import javax.sql.DataSource;

import com.baeldung.batch.model.Transaction;
import com.baeldung.batch.service.CustomItemProcessor;
import com.baeldung.batch.service.CustomSkipPolicy;
import com.baeldung.batch.service.MissingUsernameException;
import com.baeldung.batch.service.NegativeAmountException;
import com.baeldung.batch.service.RecordFieldSetMapper;
import com.baeldung.batch.service.SkippingItemProcessor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile("spring")
public class SpringBatchConfig {

    @Value("input/record.csv")
    private Resource inputCsv;

    @Value("input/recordWithInvalidData.csv")
    private Resource invalidInputCsv;

    @Value("file:xml/output.xml")
    private WritableResource outputXml;

    public ItemReader<Transaction> itemReader(Resource inputData) throws UnexpectedInputException {
        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"username", "userid", "transactiondate", "amount"};
        tokenizer.setNames(tokens);
        reader.setResource(inputData);
        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public ItemProcessor<Transaction, Transaction> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemProcessor<Transaction, Transaction> skippingItemProcessor() {
        return new SkippingItemProcessor();
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
    protected Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("itemProcessor") ItemProcessor<Transaction,
            Transaction> processor, ItemWriter<Transaction> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Transaction, Transaction> chunk(10, transactionManager)
                .reader(itemReader(inputCsv))
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(JobRepository jobRepository, @Qualifier("step1") Step step1) {
        return new JobBuilder("firstBatchJob", jobRepository).preventRestart().start(step1).build();
    }

    @Bean
    public Step skippingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("skippingItemProcessor") ItemProcessor<Transaction,
            Transaction> processor, ItemWriter<Transaction> writer) {
        return new StepBuilder("skippingStep", jobRepository)
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

    @Bean(name = "skippingBatchJob")
    public Job skippingJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("skippingStep") Step skippingStep) {
        return new JobBuilder("skippingBatchJob", jobRepository)
                .start(skippingStep)
                .preventRestart()
                .build();
    }

    @Bean
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

    @Bean(name = "skipPolicyBatchJob")
    public Job skipPolicyBatchJob(JobRepository jobRepository, @Qualifier("skipPolicyStep") Step skipPolicyStep) {
        return new JobBuilder("skipPolicyBatchJob", jobRepository)
                .start(skipPolicyStep)
                .preventRestart()
                .build();
    }

    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager() {
        return new ResourcelessTransactionManager();
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

    @Bean(name = "jobLauncher")
    public JobLauncher getJobLauncher() throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        // TaskExecutorJobLauncher's methods Throws Generic Exception,
        // it would have been better to have a specific one
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }
}
