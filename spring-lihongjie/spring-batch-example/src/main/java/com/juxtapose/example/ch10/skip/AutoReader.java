/**
 * 
 */
package com.juxtapose.example.ch10.skip;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-20下午10:06:32
 */
public class AutoReader implements ItemReader<String> {
	private int count = 0;
	private int maxCount = 30;
	
	public String read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		System.out.println("Read:" + count);
		if(count > maxCount){
			return null;
		}else{
			return ++count + "";
		}
	}
	
	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

}
