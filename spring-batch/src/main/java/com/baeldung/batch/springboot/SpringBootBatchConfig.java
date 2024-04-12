package com.baeldung.batch.springboot;

import com.baeldung.batch.model.Transaction;
import com.baeldung.batch.service.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@Profile("spring-boot")
public class SpringBootBatchConfig {

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
    public ItemWriter<Transaction> itemWriter3(Marshaller marshaller) {
        StaxEventItemWriter<Transaction> itemWriter3 = new StaxEventItemWriter<>();
        itemWriter3.setMarshaller(marshaller);
        itemWriter3.setRootTagName("transactionRecord");
        itemWriter3.setResource(outputXml);
        return itemWriter3;
    }

    @Bean
    public Marshaller marshaller3() {
        Jaxb2Marshaller marshaller3 = new Jaxb2Marshaller();
        marshaller3.setClassesToBeBound(Transaction.class);
        return marshaller3;
    }

    @Bean(name = "step1")
    protected Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("itemProcessor") ItemProcessor<Transaction, Transaction> processor, ItemWriter<Transaction> itemWriter3) {
        return new StepBuilder("step1", jobRepository)
                .<Transaction, Transaction> chunk(10, transactionManager)
                .reader(itemReader(inputCsv))
                .processor(processor)
                .writer(itemWriter3)
                .build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1, JobRepository jobRepository) {
        return new JobBuilder("firstBatchJob", jobRepository).start(step1).build();
    }

    @Bean
    public Step skippingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("skippingItemProcessor") ItemProcessor<Transaction, Transaction> processor,
                             ItemWriter<Transaction> itemWriter3) {
        return new StepBuilder("skippingStep", jobRepository)
                .<Transaction, Transaction>chunk(10, transactionManager)
                .reader(itemReader(invalidInputCsv))
                .processor(processor)
                .writer(itemWriter3)
                .faultTolerant()
                .skipLimit(2)
                .skip(MissingUsernameException.class)
                .skip(NegativeAmountException.class)
                .build();
    }

    @Bean(name = "skippingBatchJob")
    public Job skippingJob(JobRepository jobRepository, @Qualifier("skippingStep") Step skippingStep) {
        return new JobBuilder("skippingBatchJob", jobRepository)
                .start(skippingStep)
                .build();
    }

    @Bean(name = "skipPolicyStep")
    public Step skipPolicyStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("skippingItemProcessor") ItemProcessor<Transaction, Transaction> processor,
                               ItemWriter<Transaction> itemWriter3) {
        return new StepBuilder("skipPolicyStep", jobRepository)
                .<Transaction, Transaction>chunk(10, transactionManager)
                .reader(itemReader(invalidInputCsv))
                .processor(processor)
                .writer(itemWriter3)
                .faultTolerant()
                .skipPolicy(new CustomSkipPolicy())
                .build();
    }

    @Bean(name = "skipPolicyBatchJob")
    public Job skipPolicyBatchJob(JobRepository jobRepository, @Qualifier("skipPolicyStep") Step skipPolicyStep) {
        return new JobBuilder("skipPolicyBatchJob", jobRepository)
                .start(skipPolicyStep)
                .build();
    }
}
