/**
 * 
 */
package com.juxtapose.example.ch10.retry.template;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午11:00:35
 */
public class RetryTemplateExample {
	
	/**
	 * 
	 */
	public static void retrySimpleRetry(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(3);
		retry(retryCallback, retryPolicy, null, null);
	}
	
	/**
	 * 
	 */
	public static void retrySimpleRetryAndRecovery(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(5);
		RecoveryCallback<String> recoveryCallback = new DefaultRecoveryCallback<String>();
		retry(retryCallback, retryPolicy, recoveryCallback, null);
	}
	
	/**
	 * 
	 */
	public static void retryTimeout(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		TimeoutRetryPolicy retryPolicy = new TimeoutRetryPolicy();
		retryPolicy.setTimeout(3000L);
		retry(retryCallback, retryPolicy, null, null);
	}
	
	/**
	 * 3秒范围内重试
	 * 逻辑执行后休息500ms
	 * 重试后，休息500ms
	 * 总执行次数在3次左右
	 */
	public static void retryTimeoutAndRecoveryAndBackOff(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		TimeoutRetryPolicy retryPolicy = new TimeoutRetryPolicy();
		retryPolicy.setTimeout(3000L);
		BackOffPolicy backOffPolicy = new DefaultBackoffPolicy();
		RecoveryCallback<String> recoveryCallback = new DefaultRecoveryCallback<String>();
		retry(retryCallback, retryPolicy, recoveryCallback, backOffPolicy);
	}
	
	/**
	 * 
	 * @param retryCallback
	 * @param retryPolicy
	 * @param recoveryCallback
	 * @param backOffPolicy
	 */
	public static void retry(RetryCallback<String> retryCallback, RetryPolicy retryPolicy, RecoveryCallback<String> recoveryCallback, BackOffPolicy backOffPolicy){
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(retryPolicy);
		if(null != backOffPolicy)
			template.setBackOffPolicy(backOffPolicy);
		try {
			template.execute(retryCallback, recoveryCallback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		retryTimeout();
//		retrySimpleRetry();
//		retrySimpleRetryAndRecovery();
		retryTimeoutAndRecoveryAndBackOff();
	}
}
