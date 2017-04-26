package com.baeldung.spring_groovy_config;

import org.springframework.stereotype.Component;

@Component
public class TestClass {
    private String testString;
    private double testDouble;

    public TestClass(String testString, double testDouble){
        this.testString = testString;
	this.testDouble = testDouble;
    }
	
    public String getTestString(){
	return this.testString;
    }
	
    public double getTestDouble(){
	return this.testDouble;
    }
}
