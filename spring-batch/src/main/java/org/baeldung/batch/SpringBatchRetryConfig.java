package org.baeldung.batch;

import org.apache.http.conn.ConnectTimeoutException;
import org.baeldung.batch.model.Transaction;
import org.baeldung.batch.service.RecordFieldSetMapper;
import org.baeldung.batch.service.RetryItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.text.ParseException;

@Configuration
@EnableBatchProcessing
public class SpringBatchRetryConfig {
    
    private static final String[] tokens = { "username", "userid", "transactiondate", "amount" };

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("input/recordRetry.csv")
    private Resource inputCsv;

    @Value("file:xml/retryOutput.xml")
    private Resource outputXml;

    public ItemReader<Transaction> itemReader(Resource inputData) throws Exception {
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
    public Step retryStep(@Qualifier("retryItemProcessor") ItemProcessor<Transaction, Transaction> processor
      , ItemWriter<Transaction> writer) throws ParseException {
        return stepBuilderFactory.get("retryStep")
          .<Transaction, Transaction>chunk(10)
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
    public Job retryJob(@Qualifier("retryStep") Step retryStep) {
        return jobBuilderFactory
          .get("retryBatchJob")
          .start(retryStep)
          .build();
    }
}
