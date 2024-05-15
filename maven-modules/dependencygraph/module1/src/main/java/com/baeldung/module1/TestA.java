package com.baeldung.module1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.module2.TestB;

public class TestA {

    public Logger getLogger() {
        return LoggerFactory.getLogger(TestA.class);
    }

    public void printName() {
        TestB testb = new TestB();
        testb.getName("baeldung");

    }
}