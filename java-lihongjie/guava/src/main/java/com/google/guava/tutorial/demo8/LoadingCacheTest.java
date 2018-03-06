package com.google.guava.tutorial.demo8;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class LoadingCacheTest {

	public static void main(String[] args) {

		LoadingCache<String, User> userCache = CacheBuilder.newBuilder()
				.maximumSize(100)
				.expireAfterWrite(10, TimeUnit.SECONDS)
				.build(new CacheLoader<String, User>() {

					@Override
					public User load(String uid) throws Exception {
						
						return getFromDatabase(uid);
					}
					
				});
		
	      try {			
	          //on first invocation, cache will be populated with corresponding
	          //employee record
	          System.out.println("Invocation #1");
	          System.out.println(userCache.get("1"));
	          System.out.println(userCache.get("2"));
	          System.out.println(userCache.get("3"));
	          
	          //second invocation, data will be returned from cache
	          System.out.println("Invocation #2");
	          System.out.println(userCache.get("1"));
	          System.out.println(userCache.get("2"));
	          System.out.println(userCache.get("3"));
	          
	          

	       }catch (ExecutionException e) {
	          e.printStackTrace();
	       }
	}
	
	private static User getFromDatabase(String uid) {
		
		User user1 = new User(1L, "Mahesh", 20);
		User user2 = new User(2L, "Mahesh", 20);
		User user3 = new User(3L, "Mahesh", 20);
		
		Map<String, User> database = new HashMap<String, User>();
		database.put("1", user1);
		database.put("2", user2);
		database.put("3", user3);
		
		System.out.println("Database hit for " + uid);
		return database.get(uid);
	}
	
	

}
