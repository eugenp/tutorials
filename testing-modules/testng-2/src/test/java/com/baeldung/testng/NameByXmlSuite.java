package com.baeldung.testng;

import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NameByXmlSuite {

    @BeforeTest
    public void xmlName(ITestContext ctx) {
        System.out.println("XML test: " + ctx.getName());
    }
    
    @Test
    public void foo() {
        
    }
}
