/**
 * 
 */
package test.com.juxtapose.example.ch11;

import java.util.Date;

import org.springframework.batch.core.JobParametersBuilder;

import test.com.juxtapose.example.JobLaunchBase;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2014-3-23下午08:05:38
 */
public class JobLaunchChunkRemote {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobLaunchBase.executeJob("ch11/job/job-chunk-remote.xml", "remoteChunkJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
