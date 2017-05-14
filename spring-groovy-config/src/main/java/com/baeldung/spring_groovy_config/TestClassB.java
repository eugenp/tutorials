package com.baeldung.spring_groovy_config;

import org.springframework.stereotype.Component;

@Component
public class TestClassB {
    private String testStringB;
    private int testIntB;
	
    public void setTestStringB(String testStringB){
        this.testStringB = testStringB;
    }
	
    public String getTestStringB(){
        return this.testStringB;
    }
	
	
    public void setTestIntB(int testIntB){
        this.testIntB = testIntB;
    }
	
    public int getTestIntB(){
        return this.testIntB;
    }
}
