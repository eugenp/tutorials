/**
 * 
 */
package com.juxtapose.example.ch05.step.listener;

import org.springframework.batch.core.ItemReadListener;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2012-9-1上午08:09:37
 */
public class SystemOutItemReadListener implements ItemReadListener<String> {

	public void beforeRead() {
		System.out.println("ItemReadListener.beforeRead()");
	}

	public void afterRead(String item) {
		System.out.println("ItemReadListener.afterRead()");
	}

	public void onReadError(Exception ex) {
		System.out.println("ItemReadListener.onReadError()");
	}

}
