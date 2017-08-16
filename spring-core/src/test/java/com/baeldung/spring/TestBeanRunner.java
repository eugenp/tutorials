/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.spring;

/**
 *
 * @author morrisonidiasirue
 */
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestBeanRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestBeanIntegrationTest.class);

        for (Failure failure
                : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }

}
