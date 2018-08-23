package com.baeldung.logging.app;

import static java.lang.System.Logger.*;

public class MainApp {

    private static System.Logger LOGGER1 = System.getLogger("MainApp");

    public static void main(String[] args) {
        LOGGER1.log(Level.ERROR, "error test");
        LOGGER1.log(Level.INFO, "info test");
    }
}
