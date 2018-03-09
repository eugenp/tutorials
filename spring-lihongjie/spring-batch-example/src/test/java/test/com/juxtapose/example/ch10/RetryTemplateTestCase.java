package test.com.juxtapose.example.ch10;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.classify.Classifier;
import org.springframework.classify.ClassifierSupport;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryState;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;

import com.juxtapose.example.ch10.retry.template.CountHelper;
import com.juxtapose.example.ch10.retry.template.CountRetryListener;
import com.juxtapose.example.ch10.retry.template.DefaultBackoffPolicy;
import com.juxtapose.example.ch10.retry.template.DefaultRecoveryCallback;
import com.juxtapose.example.ch10.retry.template.DefaultRetryCallback;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午10:59:02
 */
public class RetryTemplateTestCase {
	@Before
	public void setUp() throws Exception {
		CountHelper.init();
	}
	

	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void testSimpleRetry(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(3);
		RetryListener[] listeners = new RetryListener[]{new CountRetryListener()};
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(retryPolicy);
		template.setListeners(listeners);
		try {
			template.execute(retryCallback);
			Assert.assertFalse(true);
		} catch (Exception e) {
			Assert.assertTrue(true);
			Assert.assertEquals(3, CountHelper.getCount());
		}
	}
	
	@Test
	public void testTimeoutRetry(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback(500L);
		TimeoutRetryPolicy retryPolicy = new TimeoutRetryPolicy();
		retryPolicy.setTimeout(1100L);
		RetryListener[] listeners = new RetryListener[]{new CountRetryListener()};
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(retryPolicy);
		template.setListeners(listeners);
		try {
			template.execute(retryCallback);
			Assert.assertFalse(true);
		} catch (Exception e) {
			Assert.assertTrue(true);
			Assert.assertEquals(3, CountHelper.getCount());
		}
	}
	
	@Test
	public void testBackoffPolicy(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(3);
		RetryListener[] listeners = new RetryListener[]{new CountRetryListener()};
		BackOffPolicy backOffPolicy = new DefaultBackoffPolicy();
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(retryPolicy);
		template.setListeners(listeners);
		template.setBackOffPolicy(backOffPolicy);
		try {
			template.execute(retryCallback);
			Assert.assertFalse(true);
		} catch (Exception e) {
			Assert.assertTrue(true);
			Assert.assertEquals(1, CountHelper.getCount());
		}
	}
	
	@Test
	public void testRecoveryCallback(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(3);
		RetryListener[] listeners = new RetryListener[]{new CountRetryListener()};
		RecoveryCallback<String> recoveryCallback = new DefaultRecoveryCallback<String>();
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(retryPolicy);
		template.setListeners(listeners);
		try {
			template.execute(retryCallback,recoveryCallback);
			Assert.assertTrue(true);
			Assert.assertEquals(2, CountHelper.getCount());
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testSimpleRetryPolicyStatefull(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(3);
		RetryListener[] listeners = new RetryListener[]{new CountRetryListener()};
		BackOffPolicy backOffPolicy = new DefaultBackoffPolicy();
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(retryPolicy);
		template.setListeners(listeners);
		template.setBackOffPolicy(backOffPolicy);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Classifier<? super Throwable, Boolean> classifier = new ClassifierSupport(Boolean.FALSE);
		RetryState retryState = new DefaultRetryState("key", false, classifier);
		try {
			template.execute(retryCallback,retryState);
			Assert.assertFalse(true);
		} catch (Exception e) {
			Assert.assertTrue(true);
			Assert.assertEquals(1, CountHelper.getCount());
		}
	}
}
