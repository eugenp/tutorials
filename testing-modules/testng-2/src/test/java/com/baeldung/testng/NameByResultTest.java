package com.baeldung.testng;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;

public class NameByResultTest {

    @BeforeMethod
    public void capture(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        long startTime = result.getStartMillis();
        log(testName, startTime);
    }
    
    public void log(String testName, long startTime) {
        
    }
}
