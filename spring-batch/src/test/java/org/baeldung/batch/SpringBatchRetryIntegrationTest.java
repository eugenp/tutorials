package org.baeldung.batch;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.baeldung.batch.service.RetryItemProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { SpringBatchRetryConfig.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class SpringBatchRetryIntegrationTest {

    private static final String TEST_OUTPUT = "xml/retryOutput.xml";

    private static final String EXPECTED_OUTPUT = "src/test/resources/output/batchRetry/retryOutput.xml";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private CloseableHttpResponse httpResponse;

    @InjectMocks
    @Autowired
    private RetryItemProcessor retryItemProcessor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenEndpointAlwaysFailing_thenJobFails() throws Exception {
        when(httpClient.execute(any())).thenThrow(new ConnectTimeoutException("Endpoint is down"));

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        assertThat(actualJobInstance.getJobName(), is("retryBatchJob"));
        assertThat(actualJobExitStatus.getExitCode(), is("FAILED"));
        assertThat(actualJobExitStatus.getExitDescription(), containsString("org.apache.http.conn.ConnectTimeoutException"));
    }

    @Test
    public void whenEndpointFailsTwiceAndPassesThirdTime_thenSuccess() throws Exception {
        FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
        FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);

        //fails for first two calls and passes third time onwards
        when(httpResponse.getEntity()).thenReturn(new StringEntity("{ \"age\":10, \"postCode\":\"430222\" }"));
        when(httpClient.execute(any()))
            .thenThrow(new ConnectTimeoutException("Timeout count 1"))
            .thenThrow(new ConnectTimeoutException("Timeout count 2"))
            .thenReturn(httpResponse);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        assertThat(actualJobInstance.getJobName(), is("retryBatchJob"));
        assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }
    
    private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("jobID", String.valueOf(System.currentTimeMillis()));
        return paramsBuilder.toJobParameters();
    }
}
