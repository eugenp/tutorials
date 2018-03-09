/**
 * 
 */
package com.juxtapose.example.ch10.retry.template;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-22下午10:58:15
 */
public class CountHelper {
	private static AtomicInteger count = new AtomicInteger(0);
	public static void init(){
		count = new AtomicInteger(0);
	}
	
	public static int getCount(){
		return count.get();
	}
	
	public static void increment(){
		count.incrementAndGet();
	}
	public static int incrementAndGet(){
		return count.incrementAndGet();
	}
	
	public static void decrement(){
		count.decrementAndGet();
	}
	
	public static int decrementAndGet(){
		return count.decrementAndGet();
	}
}
