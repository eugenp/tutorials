package test.com.juxtapose.example.ch03;


import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
"/ch03/job/job.xml"
})
public class JobLaunchTest {
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired@Qualifier("billJob")
	private Job job;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void billJob() throws Exception {
		JobExecution result = jobLauncher.run(job, (new JobParametersBuilder()
				.addDate("date", new Date())).toJobParameters());       
		System.out.println(result.toString());     
	}
}
