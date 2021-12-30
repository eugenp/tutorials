package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class ConditionalTest {

	
	@Test
	public void conditionalAndTest() {
		
		int years = 25;
		boolean driverLicence = true;
		String result = "";
		
		if((years >= 21 && driverLicence)) {
			result = "Successful Candidate.";
		}else {
			result = "Failed Candidate.";
		}
		
		assertEquals(result, "Successful Candidate.");
		
	}
	
	@Test
	public void conditionalAndTernaryTest() {
		
		int years = 25;
		boolean driverLicence = true;
		String result = (years >= 21 && driverLicence) ?  "Successful Candidate." : "Successful Candidate.";
		
		assertEquals(result, "Successful Candidate.");
		
	}
	
	@Test
	public void conditionalOrTest() {
		
		int years = 25;
		boolean driverLicence = false;
		String result = "";
		
		if((years >= 21 || driverLicence)) {
			result = "Successful Candidate.";
		}else {
			result = "Failed Candidate.";
		}
		
		assertEquals(result, "Successful Candidate.");
		
	}
	
	@Test
	public void conditionalOrTernaryTest() {
		
		int years = 25;
		boolean driverLicence = false;
		String result = (years >= 21 || driverLicence) ?  "Successful Candidate." : "Successful Candidate.";
		
		assertEquals(result, "Successful Candidate.");
		
	}
	

	
		
}
