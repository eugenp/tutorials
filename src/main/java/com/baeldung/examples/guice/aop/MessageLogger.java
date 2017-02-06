/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.examples.guice.aop;

import com.google.inject.Inject;
import java.util.logging.Logger;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


public class MessageLogger implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] objectArray = invocation.getArguments();
        int i = 0;
        for (Object object : objectArray) {
            Logger.getAnonymousLogger().info("Sending message: " + object.toString());
        }
        return invocation.proceed();
    }
}
