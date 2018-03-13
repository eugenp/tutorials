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
public class JobLaunchJpa {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobLaunchBase.executeJob("ch07/job/job-db-jpa.xml", "jpaWriteJob",
				new JobParametersBuilder().addDate("date", new Date())
				.addString("id_begin", "1").addString("id_end", "5"));
	}
}
