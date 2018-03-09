/**
 * 
 */
package com.juxtapose.example.ch04.scheduler;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-19下午03:53:39
 */
public class SchedulerLauncher {
	private Job job;
	private JobLauncher jobLauncher;
	
	public void launch() throws Exception {
		JobParameters jobParams = createJobParameters();
		jobLauncher.run(job, jobParams);
	}

	private JobParameters createJobParameters() {
		JobParameters jobParams = new JobParametersBuilder().addDate("executeDate", new Date()).toJobParameters();
		return jobParams;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public JobLauncher getJobLauncher() {
		return jobLauncher;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}
}
