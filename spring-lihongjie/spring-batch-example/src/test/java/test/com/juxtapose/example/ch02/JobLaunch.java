/**
 * 
 */
package test.com.juxtapose.example.ch02;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-2-28下午08:34:48
 */
public class JobLaunch {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"ch02/job/job.xml");
		JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("billJob");
		try {
			JobExecution result = launcher.run(job, new JobParameters());
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
