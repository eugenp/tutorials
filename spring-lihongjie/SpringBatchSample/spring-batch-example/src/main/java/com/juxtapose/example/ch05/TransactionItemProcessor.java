/**
 * 
 */
package com.juxtapose.example.ch05;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-25下午11:59:14
 */
public class TransactionItemProcessor implements ItemProcessor<String, String> {
	private String errorCount = "3";

	public String process(String item) throws Exception {
		System.out.println("TransactionItemProcessor.process() item is:" + item);
		if(errorCount.equals(item)){
			throw new RuntimeException("make error!");
		}
		return item;
	}
	
	public String getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(String errorCount) {
		this.errorCount = errorCount;
	}
}
