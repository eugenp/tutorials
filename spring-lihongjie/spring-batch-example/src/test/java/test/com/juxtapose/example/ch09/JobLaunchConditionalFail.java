/**
 * 
 */
package test.com.juxtapose.example.ch09;

import java.util.Date;

import org.springframework.batch.core.JobParametersBuilder;

import test.com.juxtapose.example.JobLaunchBase;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-30上午11:11:09
 */
public class JobLaunchConditionalFail {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobLaunchBase.executeJob("ch09/job/job-conditional-fail.xml", "conditionalFailJob",
				new JobParametersBuilder().addDate("date", new Date())
						.addString("inputFile", "classpath:ch09/data/credit-card-bill-201310-notexist.zip")
						.addString("readFileName", "credit-card-bill-201310.csv")
						.addString("workDirectory", "file:target/ch09/work/")
						.addString("writeTarget","file:target/ch09/conditional_fail/outputFile.csv"));
	}
}
