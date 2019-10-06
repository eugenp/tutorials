package com.baeldung.batch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.baeldung.batch.model.Book;
import com.baeldung.batch.model.BookDetails;
import com.baeldung.batch.model.BookRecord;

@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { SpringBatchConfiguration.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class })
@TestPropertySource("classpath:application-test.properties")
public class SpringBatchIntegrationTest {

    private static final String TEST_OUTPUT = "src/test/resources/actual-output.json";

    private static final String EXPECTED_OUTPUT = "src/test/resources/expected-output.json";

    private static final String TEST_INPUT = "src/test/resources/test-input.csv";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JsonFileItemWriter<Book> jsonItemWriter;

    @Autowired
    private ListItemWriter<BookDetails> listItemWriter;

    @Autowired
    private FlatFileItemReader<BookRecord> itemReader;

    private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("file.input", TEST_INPUT);
        paramsBuilder.addString("file.output", TEST_OUTPUT);
        return paramsBuilder.toJobParameters();
    }

    @Test
    public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
        // given
        FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
        FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());

        // then
        assertThat(jobExecution.getJobInstance()
            .getJobName(), is("transformBooksRecords"));
        assertThat(jobExecution.getExitStatus()
            .getExitCode(), is("COMPLETED"));
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }

    @Test
    public void givenReferenceOutput_whenStep1Executed_thenSuccess() throws Exception {

        // given
        FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
        FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1", defaultJobParameters());

        // then
        assertThat(jobExecution.getStepExecutions()
            .size(), is(1));
        assertThat(jobExecution.getExitStatus()
            .getExitCode(), is("COMPLETED"));
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }

    @Test
    public void whenStep2Executed_thenSuccess() {

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step2");

        // then
        assertThat(jobExecution.getStepExecutions()
            .size(), is(1));
        assertThat(jobExecution.getExitStatus()
            .getExitCode(), is("COMPLETED"));
        jobExecution.getStepExecutions()
            .forEach(stepExecution -> {
                assertThat(stepExecution.getWriteCount(), is(8));
            });
    }

    @Test
    public void givenMockedStep_whenWriterCalled_thenSuccess() throws Exception {

        // given
        FileSystemResource expectedResult = new FileSystemResource("src/test/resources/writer-expected-output.json");
        FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);
        Book demoBook = new Book();
        demoBook.setAuthor("lorem");
        demoBook.setName("ipsum");

        // when
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution();
        ExecutionContext executionContext = stepExecution.getExecutionContext();
        StepScopeTestUtils.doInStepScope(stepExecution, () -> {

            jsonItemWriter.open(executionContext);
            jsonItemWriter.write(Arrays.asList(demoBook));
            jsonItemWriter.close();
            return null;
        });

        // then
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }

    @Test
    public void givenMockedStep_whenListWriterCalled_thenSuccess() throws Exception {

        // given
        BookDetails bookDetails = new BookDetails();
        bookDetails.setBookFormat("lorem");
        bookDetails.setBookName("ipsum");
        bookDetails.setBookISBN("1234");
        bookDetails.setPublishingYear("1987");

        // when
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution();
        StepScopeTestUtils.doInStepScope(stepExecution, () -> {

            listItemWriter.write(Arrays.asList(bookDetails));

            // then
            assertThat(listItemWriter.getWrittenItems()
                .size(), is(1));
            return null;
        });
    }

}
