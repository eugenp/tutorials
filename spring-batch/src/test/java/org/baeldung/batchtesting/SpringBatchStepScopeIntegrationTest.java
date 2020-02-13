package org.baeldung.batchtesting;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.baeldung.batchtesting.model.Book;
import org.baeldung.batchtesting.model.BookRecord;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;


@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { SpringBatchConfiguration.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
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

    @After
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
                assertThat(bookRecord.getBookName(), is("Foundation"));
                assertThat(bookRecord.getBookAuthor(), is("Asimov I."));
                assertThat(bookRecord.getBookISBN(), is("ISBN 12839"));
                assertThat(bookRecord.getBookFormat(), is("hardcover"));
                assertThat(bookRecord.getPublishingYear(), is("2018"));
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
            jsonItemWriter.write(Arrays.asList(demoBook));
            jsonItemWriter.close();
            return null;
        });

        // then
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }
}
