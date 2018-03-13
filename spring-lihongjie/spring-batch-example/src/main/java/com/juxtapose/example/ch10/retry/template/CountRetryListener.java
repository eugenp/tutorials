/**
 * 
 */
package com.juxtapose.example.ch10.retry.template;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-22下午02:06:57
 */
public class CountRetryListener implements RetryListener {

	/* (non-Javadoc)
	 * @see org.springframework.retry.RetryListener#open(org.springframework.retry.RetryContext, org.springframework.retry.RetryCallback)
	 */
	@Override
	public <T> boolean open(RetryContext context, RetryCallback<T> callback) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.retry.RetryListener#close(org.springframework.retry.RetryContext, org.springframework.retry.RetryCallback, java.lang.Throwable)
	 */
	@Override
	public <T> void close(RetryContext context, RetryCallback<T> callback,
			Throwable throwable) {

	}

	/* (non-Javadoc)
	 * @see org.springframework.retry.RetryListener#onError(org.springframework.retry.RetryContext, org.springframework.retry.RetryCallback, java.lang.Throwable)
	 */
	@Override
	public <T> void onError(RetryContext context, RetryCallback<T> callback,
			Throwable throwable) {
		CountHelper.increment();
		System.out.println("CountRetryListener.onError().");
	}

}
