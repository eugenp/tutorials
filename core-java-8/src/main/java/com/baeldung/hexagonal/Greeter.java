package com.baeldung.hexagonal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class, crosses boundaries through the use of Ports and Adapters.
 */
public class Greeter {

    private static final Logger log = LoggerFactory.getLogger(Greeter.class);

    public static void main(String[] args) {
        GreeterPort greeter = new GreeterAdapter();
        String title = "Mr.", name = "Nicola Tesla";
        log.info("Given title='{}' and name='{}', customized generated greeting is: '{}'", title, name, greeter.greet(title, name));
    }

}
