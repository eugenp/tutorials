package com.baeldung.apache.camel.logging;

import org.apache.camel.main.Main;

public class CamelLoggingMainApp {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.configure()
            .addRoutesBuilder(new FileCopierCamelRoute());
        main.run(args);
    }
}
