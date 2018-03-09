/**
 * 
 */
package com.juxtapose.example.ch04.stop;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-19下午08:54:49
 */
public class AutoReader implements ItemReader<String> {
	private int count = 0;
	
	public String read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		return ++count + "";
	}

}
