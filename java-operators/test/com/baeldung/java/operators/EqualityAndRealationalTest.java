package com.baeldung.java.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class EqualityAndRealationalTest {

	
	@Test
	public void whenUseTheOperatorGreaterThanorEqualTo() {
			
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
	public void whenUseTheOperatorEqualTo() {
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
	public void whenUseTheOperatorNotEqualTo() {
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
	public void whenUseTheOperatorGreaterThan() {
		
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
	public void whenUseTheOperatorGreaterThanOrEqualTo() {
		
		int grade = 3;
		String result = "";
		
		if(grade >= 3) {
			result = "The guest is VIP";
		}else {
			result = "The guest isn't VIP";
		}
		
		assertEquals(result, "The guest is VIP");
		
	}
	
	@Test
	public void whenUseTheOperatorLessThanOrEqualTo() {
		
		int grade = 2;
		String result = "";
		
		if(grade <= 2) {
			result = "The guest isn't VIP";
		}else {
			result = "The guest is VIP";
		}
		
		assertEquals(result, "The guest isn't VIP");
		
	}
	
}
