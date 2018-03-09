/**
 * 
 */
package com.juxtapose.example.ch05.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-21下午10:52:28
 */
public class SystemOut {
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("SystemOut.beforeStep();" + 
				"JobExecution creat time:" + stepExecution.getJobExecution().getCreateTime());
	}

	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("SystemOut.afterStep();" + 
				"Job execute state:" + stepExecution.getStatus().toString());
		return null;
	}
}
