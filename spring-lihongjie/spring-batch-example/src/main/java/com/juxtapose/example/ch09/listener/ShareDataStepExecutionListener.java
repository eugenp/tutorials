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
 * 2013-10-7下午12:13:23
 */
public class ShareDataStepExecutionListener implements StepExecutionListener {

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#beforeStep(org.springframework.batch.core.StepExecution)
	 */
	@Override
	public void beforeStep(StepExecution stepExecution) {
		String readFileName = stepExecution.getJobParameters().getString(Constant.READ_FILE_NAME);
		String workDirectory = stepExecution.getJobParameters().getString(Constant.WORK_DIRECTORY);
		String writeTarget = stepExecution.getJobParameters().getString(Constant.WRITE_TARGET);
		stepExecution.getJobExecution().getExecutionContext()
				.putString(Constant.READ_FILE, workDirectory + readFileName);
		stepExecution
				.getJobExecution()
				.getExecutionContext()
				.putString(Constant.WRITE_FILE,writeTarget);
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#afterStep(org.springframework.batch.core.StepExecution)
	 */
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return stepExecution.getExitStatus();
	}

}
