package com.baeldung.batch;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBatchTest
@ContextConfiguration(classes=SpringBatchApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class SpringBatchIntegrationTest {

    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    
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
    
     
    
}
