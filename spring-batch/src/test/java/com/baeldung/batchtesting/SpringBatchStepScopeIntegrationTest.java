package com.baeldung.batchtesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.batch.test.AssertFile.assertFileEquals;

import java.util.List;

import com.baeldung.batchtesting.model.Book;
import com.baeldung.batchtesting.model.BookRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;


@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { SpringBatchConfiguration.class })
public class SpringBatchStepScopeIntegrationTest {

    private static final String TEST_OUTPUT = "src/test/resources/output/actual-output.json";

    private static final String EXPECTED_OUTPUT_ONE = "src/test/resources/output/expected-output-one.json";

    private static final String TEST_INPUT_ONE = "src/test/resources/input/test-input-one.csv";
    @Autowired
    private JsonFileItemWriter<Book> jsonItemWriter;

    @Autowired
    private FlatFileItemReader<BookRecord> itemReader;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("file.input", TEST_INPUT_ONE);
        paramsBuilder.addString("file.output", TEST_OUTPUT);
        return paramsBuilder.toJobParameters();
    }

    @AfterEach
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void givenMockedStep_whenReaderCalled_thenSuccess() throws Exception {

        // given
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution(defaultJobParameters());

        // when
        StepScopeTestUtils.doInStepScope(stepExecution, () -> {
            BookRecord bookRecord;
            itemReader.open(stepExecution.getExecutionContext());
            while ((bookRecord = itemReader.read()) != null) {

                // then
                assertEquals("Foundation", bookRecord.getBookName());
                assertEquals("Asimov I.", bookRecord.getBookAuthor());
                assertEquals("ISBN 12839", bookRecord.getBookISBN());
                assertEquals("hardcover", bookRecord.getBookFormat());
                assertEquals("2018", bookRecord.getPublishingYear());
            }
            itemReader.close();
            return null;
        });
    }

    @Test
    public void givenMockedStep_whenWriterCalled_thenSuccess() throws Exception {

        // given
        FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT_ONE);
        FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);
        Book demoBook = new Book();
        demoBook.setAuthor("Grisham J.");
        demoBook.setName("The Firm");
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution(defaultJobParameters());

        // when
        StepScopeTestUtils.doInStepScope(stepExecution, () -> {

            jsonItemWriter.open(stepExecution.getExecutionContext());
            jsonItemWriter.write(new Chunk<>(List.of(demoBook)));
            jsonItemWriter.close();
            return null;
        });

        // then
        assertFileEquals(expectedResult, actualResult);
    }
}
