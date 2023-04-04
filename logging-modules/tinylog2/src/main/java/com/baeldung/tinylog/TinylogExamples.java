package com.baeldung.tinylog;

import org.tinylog.Logger;

public class TinylogExamples {

    public static void main(String[] args) {
        /* Static text */

        Logger.info("Hello World!");

        /* Placeholders */

        Logger.info("Hello {}!", "Alice");
        Logger.info("π = {0.00}", Math.PI);

        /* Lazy Logging */

        Logger.debug("Expensive computation: {}", () -> compute()); // Visible in log files but not on the console

        /* Exceptions */

        int a = 42;
        int b = 0;

        try {
            int i = a / b;
        } catch (Exception ex) {
            Logger.error(ex, "Cannot divide {} by {}", a, b);
        }

        try {
            int i = a / b;
        } catch (Exception ex) {
            Logger.error(ex);
        }
    }

    private static int compute() {
        return 42; // In real applications, we would perform an expensive computation here
    }

}
