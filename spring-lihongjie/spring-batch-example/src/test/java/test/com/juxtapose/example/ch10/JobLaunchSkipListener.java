/**
 * 
 */
package test.com.juxtapose.example.ch10;

import java.util.Date;

import org.springframework.batch.core.JobParametersBuilder;

import test.com.juxtapose.example.JobLaunchBase;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-2-28下午08:34:48
 */
public class JobLaunchSkipListener {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobLaunchBase.executeJob("ch10/job/job-step-skip-listener.xml", "dbSkipJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
