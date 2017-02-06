/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.examples.guice.binding;

import com.baeldung.examples.guice.aop.MessageLogger;
import com.baeldung.examples.guice.aop.MessageSentLoggable;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 *
 * @author Tayo
 */
public class AOPModule extends AbstractModule {

    @Override
    protected void configure() {
        bindInterceptor(Matchers.any(),
                Matchers.annotatedWith(MessageSentLoggable.class),
                new MessageLogger()
        );
    }

}
