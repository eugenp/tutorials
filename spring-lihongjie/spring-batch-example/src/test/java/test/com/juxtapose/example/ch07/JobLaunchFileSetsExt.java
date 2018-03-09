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
 * 2013-9-21下午05:25:54
 */
public class JobLaunchFileSetsExt {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobLaunchBase.executeJob("ch07/job/job-filesets.xml", "extFilesetsWriterJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
