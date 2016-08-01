import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

package com.baeldung.wicket.examples;

public class HelloWorld extends WebPage {
    public HelloWorld() {
        add(new Label("hello", "Hello World!"));
    }
}
