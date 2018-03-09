
package com.juxtapose.example.ch10.retry.template;

import org.junit.Assert;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午11:00:17
 * @param <T>
 */
public class DefaultRecoveryCallback<T> implements RecoveryCallback<T> {

	public T recover(RetryContext context) throws Exception {
		Assert.assertNotNull(context.getAttribute("count"));
		CountHelper.decrement();
		return null;
	}

}
