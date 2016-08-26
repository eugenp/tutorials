
package com.baeldung.wicket.examples.helloworld;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class HelloWorldApplication extends WebApplication {
    @Override
    public Class<? extends Page> getHomePage() {
        return HelloWorld.class;
    }

    @Override
    public void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
    }

}
