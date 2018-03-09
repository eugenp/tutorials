/**
 * 
 */
package com.juxtapose.example.ch05.step.listener;

import org.springframework.batch.core.ItemProcessListener;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2012-9-1上午08:08:21
 */
public class SystemOutItemProcessListener implements ItemProcessListener<String, String> {

	public void beforeProcess(String item) {
		System.out.println("ItemProcessListener.beforeProcess()");
	}

	public void afterProcess(String item, String result) {
		System.out.println("ItemProcessListener.afterProcess()");
	}

	public void onProcessError(String item, Exception e) {
		System.out.println("ItemProcessListener.onProcessError()");
	}

}
