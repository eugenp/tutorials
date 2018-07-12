package com.baeldung.java.listInitialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ListInitializationUnitTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void listInOneLine1(){
		List<String> cities = new ArrayList(){
			//Inside declaration of the subclass
			
			//You can have multiple initializer block
			{
				System.out.println("Inside the initializer block.");
			}
			
			//You can override or declare new methods
			@Override
			public boolean add(Object o){
				System.out.println("Inside the overridden add: " + o);
				return super.add(o);
			}
			
			{
			add("New York");
			add("Rio");
			add("Tokyo");
		}};
		
		Assert.assertTrue(cities.contains("New York"));
 	}
 	
 	@Test
	public void listInOneLine2(){
		List<String> list = Arrays.asList("foo", "bar");
		
		Assert.assertTrue(list.contains("foo"));
	}
	
	@Test
	public void fixedSizeList(){
		List<String> list = Arrays.asList("foo", "bar");
		
		exception.expect(UnsupportedOperationException.class);
		list.add("baz");
	}
	
	@Test
	public void sharedReference(){
		String[] array = {"foo", "bar"};
		List<String> list = Arrays.asList(array);
		array[0] = "baz";
		Assert.assertEquals("baz",list.get(0));
	}
	
	@Test
	public void initializeFromStream(){
		List<String> list = Stream.of("foo", "bar").collect(Collectors.toList());
		
		Assert.assertTrue(list.contains("foo"));
	}
}
