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
 * 2013-8-16下午09:09:42
 */
public class JobLaunchListenerMerge extends JobLaunchBase{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		executeJob("ch07/job/job-listener.xml", "mergeChunkJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
