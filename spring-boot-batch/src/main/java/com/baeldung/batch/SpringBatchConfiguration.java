package com.baeldung.batch;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.baeldung.batch.model.Book;
import com.baeldung.batch.model.BookDetails;
import com.baeldung.batch.model.BookRecord;
import com.baeldung.batch.service.BookDetailsItemProcessor;
import com.baeldung.batch.service.BookItemProcessor;
import com.baeldung.batch.service.BookRecordFieldSetMapper;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration {

    private static Logger LOGGER = LoggerFactory.getLogger(SpringBatchConfiguration.class);

    private final String[] tokens = { "bookname", "bookauthor", "bookformat", "isbn", "publishyear" };

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public FlatFileItemReader<BookRecord> csvItemReader(@Value("#{jobParameters['file.input']}") String input) {
        FlatFileItemReaderBuilder<BookRecord> builder = new FlatFileItemReaderBuilder<>();
        FieldSetMapper<BookRecord> bookRecordFieldSetMapper = new BookRecordFieldSetMapper();
        LOGGER.info("Configuring reader to input {}, {}", input);
        return builder.name("bookRecordItemReader")
            .resource(new FileSystemResource(input))
            .delimited()
            .names(tokens)
            .fieldSetMapper(bookRecordFieldSetMapper)
            .build();
    }

    @Bean
    @StepScope
    public JsonFileItemWriter<Book> jsonItemWriter(@Value("#{jobParameters['file.output']}") String output) throws IOException {
        JsonFileItemWriterBuilder<Book> builder = new JsonFileItemWriterBuilder<>();
        JacksonJsonObjectMarshaller<Book> marshaller = new JacksonJsonObjectMarshaller<>();
        LOGGER.info("Configuring writer to output {}, {}", output);
        return builder.name("bookItemWriter")
            .jsonObjectMarshaller(marshaller)
            .resource(new FileSystemResource(output))
            .build();
    }

    @Bean
    @StepScope
    public ListItemWriter<BookDetails> listItemWriter() {
        return new ListItemWriter<BookDetails>();
    }

    @Bean
    @StepScope
    public BookItemProcessor bookItemProcessor() {
        return new BookItemProcessor();
    }

    @Bean
    @StepScope
    public BookDetailsItemProcessor bookDetailsItemProcessor() {
        return new BookDetailsItemProcessor();
    }

    @Bean
    public Step step1(ItemReader<BookRecord> csvItemReader, ItemWriter<Book> jsonItemWriter) throws IOException {
        return stepBuilderFactory.get("step1")
            .<BookRecord, Book> chunk(3)
            .reader(csvItemReader)
            .processor(bookItemProcessor())
            .writer(jsonItemWriter)
            .build();
    }

    @Bean
    public Step step2(ItemReader<BookRecord> csvItemReader, ItemWriter<BookDetails> listItemWriter) {
        return stepBuilderFactory.get("step2")
            .<BookRecord, BookDetails> chunk(3)
            .reader(csvItemReader)
            .processor(bookDetailsItemProcessor())
            .writer(listItemWriter)
            .build();

    }

    @Bean(name="transformBooksRecords")

    public Job transformBookRecords(Step step1, Step step2) throws IOException {
        return jobBuilderFactory.get("transformBooksRecords")
            .flow(step1)
            .next(step2)
            .end()
            .build();
    }

}
