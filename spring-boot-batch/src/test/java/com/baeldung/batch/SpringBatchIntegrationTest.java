package com.baeldung.batch;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.baeldung.batch.model.Book;
import com.baeldung.batch.service.BookItemAuthorCounter;
import com.baeldung.batch.service.BookItemWriterListener;

@RunWith(SpringRunner.class)
@SpringBatchTest
@ContextConfiguration(classes=SpringBatchApplication.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@TestPropertySource("classpath:application-test.properties")
public class SpringBatchIntegrationTest {

    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    
    @Autowired
    private BookItemWriterListener listener;
    
    @Autowired
    private JsonFileItemWriter<Book> itemWriter;
    
    @MockBean
    private BookItemAuthorCounter counter;
    
    
    @Test
    public void givenAutoconfiguration_whenContextLoad_thenSuccess() {

    }
    
    @Test
    public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
        //given 
        FileSystemResource expectedResult = new FileSystemResource("src/test/resources/expected-output.json");
        FileSystemResource actualResult = new FileSystemResource("src/test/resources/actual-output.json");
        
        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        
        //then
        assertThat(jobExecution.getJobInstance().getJobName(), is("transformBooksToJson"));
        assertThat(jobExecution.getExitStatus().getExitCode(), is("COMPLETED"));
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }
    
     
    @Test
    public void givenReferenceOutput_whenStepExecuted_thenSuccess() {
        
        
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
        
        assertThat(jobExecution.getStepExecutions().size(), is(1));
        assertThat(jobExecution.getExitStatus().getExitCode(), is("COMPLETED"));
    }
    
    
    @Test
    public void givenMockedStep_whenStepExecuted_thenListenerCalled() throws Exception {
        
        Book demoBook = new Book();
        demoBook.setAuthor("lorem");
        demoBook.setName("ipsum");
        // "step1", new Random().nextLong())
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution();//"step1", Long.getLong("1"));
        StepScopeTestUtils.doInStepScope(stepExecution, () -> {
            
            System.out.println("Before triggering the write");
            itemWriter.open(stepExecution.getExecutionContext());
            itemWriter.write(Arrays.asList(demoBook));
            itemWriter.close();
            return null;
        });
//        
//        stepExecution.setExitStatus(ExitStatus.FAILED);
//        stepExecution.addFailureException(new Throwable("Random error", new Throwable()));
        
    }
}
