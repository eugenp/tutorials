/**
 * 
 */
package com.juxtapose.example.ch10.retry;

import java.util.Random;

import org.springframework.batch.item.ItemProcessor;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午10:12:01
 */
public class RadomExceptionItemProcessor implements ItemProcessor<String, String> {
	Random ra = new Random();
	
	public String process(String item) throws Exception {
		int i = ra.nextInt(10);
		System.out.println("Process " + item + "; Random i=" + i);
		if(i%2 == 0){
			throw new RuntimeException("make error!");
		}else{
			return item;
		}
	}
}
