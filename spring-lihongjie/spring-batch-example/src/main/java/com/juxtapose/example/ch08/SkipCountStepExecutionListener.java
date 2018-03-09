/**
 * 
 */
package com.juxtapose.example.ch08;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-30下午03:20:11
 */
public class SkipCountStepExecutionListener extends StepExecutionListenerSupport {
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		int skipCount = stepExecution.getSkipCount();
		System.out.println("Skip count=" + skipCount);
		return stepExecution.getExitStatus();
	}
}
