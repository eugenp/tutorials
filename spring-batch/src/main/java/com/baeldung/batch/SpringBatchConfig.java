package com.baeldung.batch;

import com.baeldung.batch.model.Transaction;
import com.baeldung.batch.service.RecordFieldSetMapper;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Profile("spring")
@EnableBatchProcessing
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
}
