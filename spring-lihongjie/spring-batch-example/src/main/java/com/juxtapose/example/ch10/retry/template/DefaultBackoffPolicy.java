/**
 * 
 */
package com.juxtapose.example.ch10.retry.template;

import org.junit.Assert;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.BackOffPolicy;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午10:59:56
 */
public class DefaultBackoffPolicy implements BackOffPolicy {

	/* (non-Javadoc)
	 * @see org.springframework.batch.retry.backoff.BackOffPolicy#start(org.springframework.batch.retry.RetryContext)
	 */
	public BackOffContext start(RetryContext context) {
		BackOffContextImpl backOffContext = new BackOffContextImpl(context);
		return backOffContext;
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.retry.backoff.BackOffPolicy#backOff(org.springframework.batch.retry.backoff.BackOffContext)
	 */
	public void backOff(BackOffContext backOffContext)
			throws BackOffInterruptedException {
		Assert.assertNotNull(((BackOffContextImpl)backOffContext).getRetryContext().getAttribute("count"));
		CountHelper.decrement();
	}

}
