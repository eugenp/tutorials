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
 * 2013-10-7下午02:45:42
 */
public class SplitStepExecutionListener implements StepExecutionListener {
	private String readFile;
	private String writeFile;

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#beforeStep(org.springframework.batch.core.StepExecution)
	 */
	@Override
	public void beforeStep(StepExecution stepExecution) {
		stepExecution.getJobExecution().getExecutionContext()
				.putString(Constant.READ_FILE, readFile);
		stepExecution.getJobExecution().getExecutionContext()
				.putString(Constant.WRITE_FILE, writeFile);
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#afterStep(org.springframework.batch.core.StepExecution)
	 */
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return stepExecution.getExitStatus();
	}

	public void setReadFile(String readFile) {
		this.readFile = readFile;
	}

	public void setWriteFile(String writeFile) {
		this.writeFile = writeFile;
	}

}
