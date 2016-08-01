import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

package com.baeldung.wicket.examples;

public class HelloWorldApplication extends WebApplication {
    @Override
    public Class<? extends Page> getHomePage() {
        return HelloWorld.class;
    }
}
