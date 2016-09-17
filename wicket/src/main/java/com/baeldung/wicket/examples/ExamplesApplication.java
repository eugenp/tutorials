package com.baeldung.wicket.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import com.baeldung.wicket.examples.cafeaddress.CafeAddress;
import com.baeldung.wicket.examples.helloworld.HelloWorld;

public class ExamplesApplication extends WebApplication {
    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return Examples.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();
        mountPage("/examples/helloworld", HelloWorld.class);
        mountPage("/examples/cafes", CafeAddress.class);
    }
}
