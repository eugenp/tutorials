/**
 * 
 */
package com.juxtapose.example.ch10.retry.template;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryListener;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-22下午08:44:02
 */
public class CreditBillTasklet implements Tasklet {

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		RetryListener[] listeners = new RetryListener[]{new CountRetryListener()};
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(3);
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(retryPolicy);
		template.setListeners(listeners);
		template.execute(retryCallback);
		return RepeatStatus.FINISHED;
	}

}
