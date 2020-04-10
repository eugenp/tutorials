package com.baeldung.batch;

import com.baeldung.batch.model.Transaction;
import com.baeldung.batch.service.CustomItemProcessor;
import com.baeldung.batch.service.CustomSkipPolicy;
import com.baeldung.batch.service.MissingUsernameException;
import com.baeldung.batch.service.NegativeAmountException;
import com.baeldung.batch.service.RecordFieldSetMapper;
import com.baeldung.batch.service.SkippingItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.text.ParseException;

public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("input/record.csv")
    private Resource inputCsv;

    @Value("input/recordWithInvalidData.csv")
    private Resource invalidInputCsv;

    @Value("file:xml/output.xml")
    private Resource outputXml;

    public ItemReader<Transaction> itemReader(Resource inputData) throws UnexpectedInputException, ParseException {
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
    protected Step step1(@Qualifier("itemProcessor") ItemProcessor<Transaction, Transaction> processor, ItemWriter<Transaction> writer) throws ParseException {
        return stepBuilderFactory
                .get("step1")
                .<Transaction, Transaction> chunk(10)
                .reader(itemReader(inputCsv))
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobBuilderFactory.get("firstBatchJob").start(step1).build();
    }

    @Bean
    public Step skippingStep(@Qualifier("skippingItemProcessor") ItemProcessor<Transaction, Transaction> processor,
                             ItemWriter<Transaction> writer) throws ParseException {
        return stepBuilderFactory
                .get("skippingStep")
                .<Transaction, Transaction>chunk(10)
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
    public Job skippingJob(@Qualifier("skippingStep") Step skippingStep) {
        return jobBuilderFactory
                .get("skippingBatchJob")
                .start(skippingStep)
                .build();
    }

    @Bean
    public Step skipPolicyStep(@Qualifier("skippingItemProcessor") ItemProcessor<Transaction, Transaction> processor,
                               ItemWriter<Transaction> writer) throws ParseException {
        return stepBuilderFactory
                .get("skipPolicyStep")
                .<Transaction, Transaction>chunk(10)
                .reader(itemReader(invalidInputCsv))
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipPolicy(new CustomSkipPolicy())
                .build();
    }

    @Bean(name = "skipPolicyBatchJob")
    public Job skipPolicyBatchJob(@Qualifier("skipPolicyStep") Step skipPolicyStep) {
        return jobBuilderFactory
                .get("skipPolicyBatchJob")
                .start(skipPolicyStep)
                .build();
    }

}
