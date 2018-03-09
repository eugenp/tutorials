/**
 * 
 */
package com.juxtapose.example.ch11.multithread;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-11-16下午10:58:12
 */
public class AutoReader implements ItemReader<String> {
	private int count = 0;
	private int maxCount = 30;
	
	public String read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		if(count > maxCount){
			return null;
		}else{
			System.out.print("Read:" + count + ";");
			System.out.println("Job Read Thread name: " + Thread.currentThread().getName());
			return count++ + "";
		}
	}
	
	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

}
