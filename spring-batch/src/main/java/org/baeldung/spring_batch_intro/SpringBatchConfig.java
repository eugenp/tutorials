package org.baeldung.spring_batch_intro;

import java.net.MalformedURLException;
import java.text.ParseException;

import org.baeldung.spring_batch_intro.model.Transaction;
import org.baeldung.spring_batch_intro.service.CustomItemProcessor;
import org.baeldung.spring_batch_intro.service.RecordFieldSetMapper;
import org.baeldung.spring_batch_partitioner.listener.JobListener;
import org.baeldung.spring_batch_partitioner.partitioner.TransactionPartitioner;
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
import org.springframework.core.task.TaskExecutor;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private TaskExecutor taskExecutor;

    @Value("input/record.csv")
    private Resource inputCsv;

    @Value("file:xml/output.xml")
    private Resource outputXml;

    @Bean
    public ItemReader<Transaction> itemReader() throws UnexpectedInputException, ParseException {
        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<Transaction>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = { "username", "userid", "transactiondate", "amount" };
        tokenizer.setNames(tokens);
        reader.setResource(inputCsv);
        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<Transaction>();
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
    public ItemWriter<Transaction> itemWriter(Marshaller marshaller) throws MalformedURLException {
        StaxEventItemWriter<Transaction> itemWriter = new StaxEventItemWriter<Transaction>();
        itemWriter.setMarshaller(marshaller);
        itemWriter.setRootTagName("transactionRecord");
        itemWriter.setResource(outputXml);
        return itemWriter;
    }

    @Bean
    public Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(new Class[] { Transaction.class });
        return marshaller;
    }

    @Bean
    protected Step step1(ItemReader<Transaction> reader, ItemProcessor<Transaction, Transaction> processor, ItemWriter<Transaction> writer) {
        return steps.get("step1").<Transaction, Transaction> chunk(10).reader(reader).processor(processor).writer(writer).build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobs.get("firstBatchJob").start(step1).build();
    }

    @Bean(name = "partitioningJob")
    public Job partitioningJob() throws UnexpectedInputException, MalformedURLException, ParseException {
        return jobs.get("partitioningJob").listener(jobListener()).start(partitionStep()).build();
    }

    @Bean
    public Step partitionStep() throws UnexpectedInputException, MalformedURLException, ParseException {
        return steps.get("partitionStep").partitioner(step2()).partitioner("step2", partitioner()).taskExecutor(taskExecutor).build();
    }

    @Bean
    public Step step2() throws UnexpectedInputException, MalformedURLException, ParseException {
        return steps.get("step2").<Transaction, Transaction> chunk(1).reader(itemReader()).processor(itemProcessor()).writer(itemWriter(marshaller())).build();
    }

    @Bean
    public TransactionPartitioner partitioner() {
        TransactionPartitioner partitioner = new TransactionPartitioner();
        partitioner.partition(10);
        return partitioner;
    }

    @Bean
    public JobListener jobListener() {
        return new JobListener();
    }

}
