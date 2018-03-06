/**
 * 
 */
package com.juxtapose.example.ch05;

import java.util.Random;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileParseException;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-19下午08:54:49
 */
public class RadomExceptionAutoReader implements ItemReader<String> {
	private int count = 0;
	private int maxCount = 30;
	private Random ra = new Random();
	
	public String read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		int i = ra.nextInt(10);
		String target = null;
		if(count > maxCount){
			return target;
		}else{
			target = ++count+ "";
			if(i%2 == 0){
				throw new FlatFileParseException(target, target, count);
			}else{
				return  target;
			}
		}
	}
	
	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

}
