package com.baeldung.batch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.baeldung.batch.model.Book;
import com.baeldung.batch.model.BookDetails;

@RunWith(SpringRunner.class)
@SpringBatchTest
@ContextConfiguration(classes = SpringBatchApplication.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class })
@TestPropertySource("classpath:application-test.properties")
public class SpringBatchIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JsonFileItemWriter<Book> jsonItemWriter;

    @Autowired
    private ListItemWriter<BookDetails> listItemWriter;

    public StepExecution getStepExection() {
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        execution.getExecutionContext()
            .putString("input.data", "foo,bar,spam");
        return execution;
    }

    @Test
    public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
        // given
        FileSystemResource expectedResult = new FileSystemResource("src/test/resources/expected-output.json");
        FileSystemResource actualResult = new FileSystemResource("src/test/resources/actual-output.json");

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

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
        FileSystemResource expectedResult = new FileSystemResource("src/test/resources/expected-output.json");
        FileSystemResource actualResult = new FileSystemResource("src/test/resources/actual-output.json");

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");

        // then
        assertThat(jobExecution.getStepExecutions()
            .size(), is(1));
        assertThat(jobExecution.getExitStatus()
            .getExitCode(), is("COMPLETED"));
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }

    @Test
    public void givenReferenceOutput_whenStep2Executed_thenSuccess() {

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step2");

        assertThat(jobExecution.getStepExecutions()
            .size(), is(1));
        assertThat(jobExecution.getExitStatus()
            .getExitCode(), is("COMPLETED"));
    }

    @Test
    public void givenMockedStep_whenWriterCalled_thenSuccess() throws Exception {

        // given
        FileSystemResource expectedResult = new FileSystemResource("src/test/resources/writer-expected-output.json");
        FileSystemResource actualResult = new FileSystemResource("src/test/resources/actual-output.json");
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
