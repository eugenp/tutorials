
package com.baeldung.examples.guice.binding;

import com.baeldung.examples.guice.aop.MessageLogger;
import com.baeldung.examples.guice.aop.MessageSentLoggable;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 *
 * @author baeldung
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
