/**
 * 
 */
package com.juxtapose.example.ch05;

import java.util.Random;

import org.springframework.batch.item.ItemProcessor;


/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-25上午05:16:21
 */
public class AlwaysExceptionItemProcessor implements ItemProcessor<String, String> {
	Random ra = new Random();
	public String process(String item) throws Exception {
		int i = ra.nextInt(10);
		if(i%2 == 0){
			System.out.println("Process " + item + "; Random i=" + i +"; Exception:MockARuntimeException");
			throw new MockARuntimeException("make error!");
		}else{
			System.out.println("Process " + item + "; Random i=" + i +"; Exception:MockBRuntimeException");
			throw new MockBRuntimeException("make error!");
		}
	}
}
