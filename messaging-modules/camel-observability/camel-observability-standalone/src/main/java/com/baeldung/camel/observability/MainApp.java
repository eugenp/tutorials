package com.baeldung.camel.observability;

import org.apache.camel.main.Main;

public class MainApp {

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.configure().addRoutesBuilder(new SimpleRouteBuilder());
        main.bind("SimpleProcessor", new SimpleProcessor());
        main.run(args);
    }

}

