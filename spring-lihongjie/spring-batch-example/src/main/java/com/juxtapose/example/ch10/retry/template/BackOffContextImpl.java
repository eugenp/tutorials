/**
 * 
 */
package com.juxtapose.example.ch10.retry.template;

import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-22下午12:43:00
 */
public class BackOffContextImpl implements BackOffContext {
	private RetryContext retryContext;
	
	public BackOffContextImpl(){}
	public BackOffContextImpl(RetryContext retryContext){
		this.retryContext = retryContext;
	}
	
	public RetryContext getRetryContext() {
		return retryContext;
	}
	public void setRetryContext(RetryContext retryContext) {
		this.retryContext = retryContext;
	}
}
