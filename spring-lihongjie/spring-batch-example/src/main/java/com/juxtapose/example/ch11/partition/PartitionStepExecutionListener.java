/**
 * 
 */
package com.juxtapose.example.ch11.partition;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;


/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2014-3-22上午10:11:29
 */
public class PartitionStepExecutionListener implements StepExecutionListener {
	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("ThreadName=" + Thread.currentThread().getName() + "; " 
				+ "StepName=" + stepExecution.getStepName() + "; "
				+ "FileName=" 
				+ stepExecution.getExecutionContext().getString("fileName"));
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}
}
