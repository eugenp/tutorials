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
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.baeldung.batch.model.Book;
import com.baeldung.batch.service.BookFieldSetMapper;
import com.baeldung.batch.service.BookItemAuthorCounter;
import com.baeldung.batch.service.BookItemProcessor;
import com.baeldung.batch.service.BookItemWriterListener;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration {

    private static Logger LOGGER = LoggerFactory.getLogger(SpringBatchConfiguration.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("${file.input}")
    private FileSystemResource input;

    @Value("${file.output}")
    private FileSystemResource output;

    @Bean
    @StepScope
    public FlatFileItemReader<Book> itemReader() {
        FlatFileItemReaderBuilder<Book> builder = new FlatFileItemReaderBuilder<>();
        BookFieldSetMapper bookFieldSetMapper = new BookFieldSetMapper();
        LOGGER.info("Configuring reader to input {}, {}", input.getPath(), input.exists());
        String[] tokens = { "bookname", "bookauthor" };
        return builder.name("bookItemReader")
            .resource(input)
            .delimited()
            .names(tokens)
            .fieldSetMapper(bookFieldSetMapper)
            .build();
    }

    @Bean
    @StepScope
    public JsonFileItemWriter<Book> itemWriter() throws IOException {
        JsonFileItemWriterBuilder<Book> builder = new JsonFileItemWriterBuilder<>();
        JacksonJsonObjectMarshaller<Book> marshaller = new JacksonJsonObjectMarshaller<>();
        LOGGER.info("Configuring writer to output {}, {}", output.getPath(), output.exists());
        return builder.name("bookItemWriter")
            .jsonObjectMarshaller(marshaller)
            .resource(output)
            .build();
    }

    @Bean
    public BookItemProcessor itemProcessor() {
        return new BookItemProcessor();
    }

    @Bean
    public BookItemWriterListener itemWriterListener() {
        return new BookItemWriterListener(counter());
    }

    @Bean
    public Step step1(FlatFileItemReader<Book> itemReader, 
                      ItemProcessor<Book, Book> itemProcessor, 
                      JsonFileItemWriter<Book> itemWriter) throws IOException {
        return stepBuilderFactory.get("step1")
            .<Book, Book> chunk(3)
            .listener(itemWriterListener())
            .reader(itemReader)
            .processor(itemProcessor)
            .writer(itemWriter)
            .build();
    }

    @Bean
    public Job transformBooksToJson(Step step1) {
        return jobBuilderFactory.get("transformBooksToJson")
            .incrementer(new RunIdIncrementer())
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public BookItemAuthorCounter counter() {
        return new BookItemAuthorCounter();
    }

}
