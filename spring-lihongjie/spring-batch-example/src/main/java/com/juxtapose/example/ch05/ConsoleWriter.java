/**
 * 
 */
package com.juxtapose.example.ch05;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-19下午08:56:21
 */
public class ConsoleWriter implements ItemWriter<String> {

	public void write(List<? extends String> items) throws Exception {
		System.out.println("Write begin:");
		for(String item : items){
			System.out.print(item + ",");
		}
		System.out.println("Write end!!");
	}

}
