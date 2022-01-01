package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class EqualityAndRealationalTest {

	
	@Test
	public void greaterThanorEqualToTest() {
			
		int testGrade = 10;
		String result = "";
		
		if(testGrade >= 7) {
			result = "Approved Student!";
		}else {
			result = "Failed Student!";
		}
		
		assertEquals(result, "Approved Student!");
	}
	
	@Test
	public void equalToTest() {
		int grade = 3;
		String result = "";
		
		if(grade == 3) {
			result = "The guest is VIP";
		}else {
			result = "The guest isn't VIP";
		}
		
		assertEquals(result, "The guest is VIP");
	}
	
	@Test
	public void notEqualToTest() {
		int grade = 2;
		String result = "";
		
		if(grade != 3) {
			result = "The guest isn't VIP";
		}else {
			result = "The guest is VIP";
		}
		
		assertEquals(result, "The guest isn't VIP");
	}
	
	@Test
	public void greaterThanTest() {
		
		int grade = 3;
		String result = "";
		
		if(grade > 2) {
			result = "The guest is VIP";
		}else {
			result = "The guest isn't VIP";
		}
		
		assertEquals(result, "The guest is VIP");
	}
	
	
	@Test
	public void lessThanOrEqualToTest() {
		
		int grade = 2;
		String result = "";
		
		if(grade <= 2) {
			result = "The guest isn't VIP";
		}else {
			result = "The guest is VIP";
		}
		
		assertEquals(result, "The guest isn't VIP");
	}
	
	@Test 
	public void greaterThanOrEqualToTest() {
		
		int grade = 3;
		String result = "";
		
		if(grade >= 3) {
			result = "The guest is VIP";
		}else {
			result = "The guest isn't VIP";
		}
		
		assertEquals(result, "The guest is VIP");
	}
	
}
