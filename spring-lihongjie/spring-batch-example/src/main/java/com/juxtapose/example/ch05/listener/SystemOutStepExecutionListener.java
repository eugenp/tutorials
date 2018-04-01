/**
 * 
 */
package com.juxtapose.example.ch05.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-21下午10:52:28
 */
public class SystemOutStepExecutionListener implements StepExecutionListener {

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#beforeStep(org.springframework.batch.core.StepExecution)
	 */
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("SystemOutStepExecutionListener.beforeStep();" + 
				"JobExecution creat time:" + stepExecution.getJobExecution().getCreateTime());
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#afterStep(org.springframework.batch.core.StepExecution)
	 */
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("SystemOutStepExecutionListener.afterStep();" + 
				"Job execute state:" + stepExecution.getStatus().toString());
		return null;
	}

}
