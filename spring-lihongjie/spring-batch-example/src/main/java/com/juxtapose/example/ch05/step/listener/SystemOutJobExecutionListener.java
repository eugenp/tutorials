/**
 * 
 */
package com.juxtapose.example.ch05.step.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2012-9-1上午08:24:11
 */
public class SystemOutJobExecutionListener implements JobExecutionListener {

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.JobExecutionListener#beforeJob(org.springframework.batch.core.JobExecution)
	 */
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("JobExecutionListener.beforeJob()");
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.JobExecutionListener#afterJob(org.springframework.batch.core.JobExecution)
	 */
	public void afterJob(JobExecution jobExecution) {
		System.out.println("JobExecutionListener.afterJob()");
	}

}
