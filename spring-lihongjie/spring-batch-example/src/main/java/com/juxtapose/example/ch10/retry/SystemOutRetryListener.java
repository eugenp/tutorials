/**
 * 
 */
package com.juxtapose.example.ch10.retry;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午10:10:26
 */
public class SystemOutRetryListener implements RetryListener {

	/* (non-Javadoc)
	 * @see org.springframework.batch.retry.RetryListener#open(org.springframework.batch.retry.RetryContext, org.springframework.batch.retry.RetryCallback)
	 */
	public <T> boolean open(RetryContext context, RetryCallback<T> callback) {
		System.out.println("SystemOutRetryListener.open()");
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.retry.RetryListener#close(org.springframework.batch.retry.RetryContext, org.springframework.batch.retry.RetryCallback, java.lang.Throwable)
	 */
	public <T> void close(RetryContext context, RetryCallback<T> callback,
			Throwable throwable) {
		System.out.println("SystemOutRetryListener.close()");
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.retry.RetryListener#onError(org.springframework.batch.retry.RetryContext, org.springframework.batch.retry.RetryCallback, java.lang.Throwable)
	 */
	public <T> void onError(RetryContext context, RetryCallback<T> callback,
			Throwable throwable) {
		System.out.println("SystemOutRetryListener.onError()");
	}

}
