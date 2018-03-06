/**
 * 
 */
package com.juxtapose.example.ch04.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-9下午08:46:28
 */
public class SystemOutJobExecutionListener implements JobExecutionListener {

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.JobExecutionListener#beforeJob(org.springframework.batch.core.JobExecution)
	 */
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("JobExecution creat time:" + jobExecution.getCreateTime());
//		throw new RuntimeException("listener make error!");
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.JobExecutionListener#afterJob(org.springframework.batch.core.JobExecution)
	 */
	public void afterJob(JobExecution jobExecution) {
		System.out.println("Job execute state:" + jobExecution.getStatus().toString());
	}

}
