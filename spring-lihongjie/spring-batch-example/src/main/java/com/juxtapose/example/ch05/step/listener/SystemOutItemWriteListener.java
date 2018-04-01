/**
 * 
 */
package com.juxtapose.example.ch05.step.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2012-9-1上午08:11:52
 */
public class SystemOutItemWriteListener implements ItemWriteListener<String> {

	public void beforeWrite(List<? extends String> items) {
		System.out.println("ItemWriteListener.beforeWrite()");		
	}

	public void afterWrite(List<? extends String> items) {
		System.out.println("ItemWriteListener.afterWrite()");		
	}

	public void onWriteError(Exception exception, List<? extends String> items) {
		System.out.println("ItemWriteListener.onWriteError()");		
	}

}
