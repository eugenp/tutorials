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
 * 2013-10-21下午10:30:00
 */

public class JobLaunchRetryPolicy {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobLaunchBase.executeJob("ch10/job/job-step-retry.xml", "retryPolicyJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
