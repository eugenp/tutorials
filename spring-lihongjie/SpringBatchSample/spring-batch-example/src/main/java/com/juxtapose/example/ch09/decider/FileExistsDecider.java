/**
 * 
 */
package com.juxtapose.example.ch09.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import com.juxtapose.example.ch09.Constant;
import com.juxtapose.example.ch09.CreditService;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-7上午10:23:12
 */
public class FileExistsDecider implements JobExecutionDecider {
	private CreditService creditService;

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.job.flow.JobExecutionDecider#decide(org.springframework.batch.core.JobExecution, org.springframework.batch.core.StepExecution)
	 */
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {
		String readFileName = stepExecution.getJobParameters().getString(Constant.READ_FILE_NAME);
		String workDirectory = stepExecution.getJobParameters().getString(Constant.WORK_DIRECTORY);
		if(creditService.exists(workDirectory+readFileName)) {
			return new FlowExecutionStatus("FILE EXISTS");
		} else {
			return new FlowExecutionStatus("NO FILE");
		}
	}

	public void setCreditService(CreditService creditService) {
		this.creditService = creditService;
	}

}
