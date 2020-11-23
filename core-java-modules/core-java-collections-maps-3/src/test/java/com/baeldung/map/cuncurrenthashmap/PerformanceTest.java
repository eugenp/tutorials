package com.baeldung.map.cuncurrenthashmap;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

public class PerformanceTest {
	
	@Test(expected = ConcurrentModificationException.class)
	public void whenRemoveAndAddOnHashMap_thenCuncurrentModificationError() {
		Map<UserId, String> map = new HashMap<>();
		Map<UserId, String> synchronizedMap = Collections.synchronizedMap(map);
		long startTime = System.currentTimeMillis();
		for(int i=0; i<100000; i++) {
			UserId userId = new UserId(1);
			synchronizedMap.put(userId, userId.toString());
		}
		long endTime = System.currentTimeMillis();
		long addTimeForSynchronized = endTime-startTime;
		
		startTime = System.currentTimeMillis();
		for(int i=0; i<100000; i++) {
			UserId userId = new UserId(1);
			synchronizedMap.get(userId);
		}
		endTime = System.currentTimeMillis();
		long fetchTimeForSynchronized = endTime-startTime;
		
		Map<UserId, String> map1 = new ConcurrentHashMap<>();
		startTime = System.currentTimeMillis();
		for(int i=0; i<100000; i++) {
			UserId userId = new UserId(1);
			map1.put(userId, userId.toString());
		}
		endTime = System.currentTimeMillis();
		long addTimeForConcurrent = endTime-startTime;
		
		startTime = System.currentTimeMillis();
		for(int i=0; i<100000; i++) {
			UserId userId = new UserId(1);
			map1.get(userId);
		}
		endTime = System.currentTimeMillis();
		long fetchTimeForConcurrent = endTime-startTime;

		System.out.println("ABC");
		
	}

}
