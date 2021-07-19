package com.baeldung.batchtesting;

import java.io.IOException;

import com.baeldung.batchtesting.model.Book;
import com.baeldung.batchtesting.model.BookDetails;
import com.baeldung.batchtesting.model.BookRecord;
import com.baeldung.batchtesting.service.BookDetailsItemProcessor;
import com.baeldung.batchtesting.service.BookItemProcessor;
import com.baeldung.batchtesting.service.BookRecordFieldSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
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


@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration {

    private static Logger LOGGER = LoggerFactory.getLogger(SpringBatchConfiguration.class);

    private static final String[] TOKENS = { "bookname", "bookauthor", "bookformat", "isbn", "publishyear" };

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public FlatFileItemReader<BookRecord> csvItemReader(@Value("#{jobParameters['file.input']}") String input) {
        FlatFileItemReaderBuilder<BookRecord> builder = new FlatFileItemReaderBuilder<>();
        FieldSetMapper<BookRecord> bookRecordFieldSetMapper = new BookRecordFieldSetMapper();
        LOGGER.info("Configuring reader to input {}", input);
        // @formatter:off
        return builder
          .name("bookRecordItemReader")
          .resource(new FileSystemResource(input))
          .delimited()
          .names(TOKENS)
          .fieldSetMapper(bookRecordFieldSetMapper)
          .build();
        // @formatter:on
    }

    @Bean
    @StepScope
    public JsonFileItemWriter<Book> jsonItemWriter(@Value("#{jobParameters['file.output']}") String output) throws IOException {
        JsonFileItemWriterBuilder<Book> builder = new JsonFileItemWriterBuilder<>();
        JacksonJsonObjectMarshaller<Book> marshaller = new JacksonJsonObjectMarshaller<>();
        LOGGER.info("Configuring writer to output {}", output);
        // @formatter:off
        return builder
          .name("bookItemWriter")
          .jsonObjectMarshaller(marshaller)
          .resource(new FileSystemResource(output))
          .build();
        // @formatter:on
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
        // @formatter:off
        return stepBuilderFactory
          .get("step1")
          .<BookRecord, Book> chunk(3)
          .reader(csvItemReader)
          .processor(bookItemProcessor())
          .writer(jsonItemWriter)
          .build();
        // @formatter:on
    }

    @Bean
    public Step step2(ItemReader<BookRecord> csvItemReader, ItemWriter<BookDetails> listItemWriter) {
        // @formatter:off
        return stepBuilderFactory
          .get("step2")
          .<BookRecord, BookDetails> chunk(3)
          .reader(csvItemReader)
          .processor(bookDetailsItemProcessor())
          .writer(listItemWriter)
          .build();
        // @formatter:on
    }

    @Bean(name = "transformBooksRecords")
    public Job transformBookRecords(Step step1, Step step2) throws IOException {
        // @formatter:off
        return jobBuilderFactory
          .get("transformBooksRecords")
          .flow(step1)
          .next(step2)
          .end()
          .build();
        // @formatter:on
    }

}
