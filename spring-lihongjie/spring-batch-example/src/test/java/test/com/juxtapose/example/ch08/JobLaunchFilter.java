/**
 * 
 */
package test.com.juxtapose.example.ch08;

import java.util.Date;

import org.springframework.batch.core.JobParametersBuilder;

import test.com.juxtapose.example.JobLaunchBase;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-30上午11:11:09
 */
public class JobLaunchFilter {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobLaunchBase.executeJob("ch08/job/job-filter.xml", "filterJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
