package org.baeldung.customscope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "customScope")
public class MyBean {

    public void sayHello() {
        System.out.println("Hello from " + this.getClass().getName());
    }
}
