package test.com.juxtapose.example.ch02;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/ch02/job/job.xml"
})
public class JobLaunchTest {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("billJob")
    private Job job;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void billJob() throws Exception {
        JobExecution result = jobLauncher.run(job, new JobParameters());
        System.out.println(result.toString());
    }
}
