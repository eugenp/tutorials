/**
 * 
 */
package com.juxtapose.example.ch10.retry.template;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午11:00:27
 */
public class DefaultRetryCallback implements RetryCallback<String> {
	private long sleepTime = 0L;
	
	public DefaultRetryCallback(){}
	public DefaultRetryCallback(long sleepTime){
		this.sleepTime = sleepTime;
	}

	public String doWithRetry(RetryContext context) throws Exception {
		Integer count = (Integer)context.getAttribute("count");
		if(count == null){
			count = new Integer(0);
		}
		count++;
		context.setAttribute("count", count);
		Thread.sleep(sleepTime);
		throw new RuntimeException("Mock make exception on business logic.");
	}
}
