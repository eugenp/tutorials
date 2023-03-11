package com.baeldung.batchtesting;

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
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfiguration {

    private static Logger LOGGER = LoggerFactory.getLogger(SpringBatchConfiguration.class);

    private static final String[] TOKENS = { "bookname", "bookauthor", "bookformat", "isbn", "publishyear" };

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
    public JsonFileItemWriter<Book> jsonItemWriter(@Value("#{jobParameters['file.output']}") String output) {
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
        return new ListItemWriter<>();
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

    @Bean(name = "step1")
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemReader<BookRecord> csvItemReader, ItemWriter<Book> jsonItemWriter) {
        // @formatter:off
        return new StepBuilder("step1", jobRepository)
          .<BookRecord, Book> chunk(3, transactionManager)
          .reader(csvItemReader)
          .processor(bookItemProcessor())
          .writer(jsonItemWriter)
          .build();
        // @formatter:on
    }

    @Bean(name = "step2")
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemReader<BookRecord> csvItemReader, ItemWriter<BookDetails> listItemWriter) {
        // @formatter:off
        return new StepBuilder("step2", jobRepository)
          .<BookRecord, BookDetails> chunk(3, transactionManager)
          .reader(csvItemReader)
          .processor(bookDetailsItemProcessor())
          .writer(listItemWriter)
          .build();
        // @formatter:on
    }

    @Bean(name = "transformBooksRecords")
    public Job transformBookRecords(JobRepository jobRepository, Step step1, Step step2) {
        // @formatter:off
        return new JobBuilder("transformBooksRecords", jobRepository)
          .flow(step1)
          .next(step2)
          .end()
          .build();
        // @formatter:on
    }
}
