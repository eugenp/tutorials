/**
 * 
 */
package com.juxtapose.example.ch09.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import com.juxtapose.example.ch09.Constant;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-7下午03:33:22
 */
public class VerifyStepExecutionListener implements StepExecutionListener {

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#beforeStep(org.springframework.batch.core.StepExecution)
	 */
	@Override
	public void beforeStep(StepExecution stepExecution) { }

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#afterStep(org.springframework.batch.core.StepExecution)
	 */
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		String status = stepExecution.getExecutionContext().getString(Constant.VERITY_STATUS);
		if(null != status){
			return new ExitStatus(status);
		}
		return stepExecution.getExitStatus();
	}

}
