/**
 * 
 */
package test.com.juxtapose.example.ch07;

import java.util.Date;

import org.springframework.batch.core.JobParametersBuilder;

import test.com.juxtapose.example.JobLaunchBase;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-29下午12:36:33
 */
public class JobLaunchRestartableCustomItemWriter {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobLaunchBase.executeJob("ch07/job/job-custom-itemwriter.xml", "restartableCustomItemWriteJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
