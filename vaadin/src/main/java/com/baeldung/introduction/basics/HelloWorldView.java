package com.baeldung.introduction.basics;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("hello-world")
public class HelloWorldView extends VerticalLayout {

    public HelloWorldView() {
        add(new H1("Hello, World!"));
    }
}
