package com.baeldung.logging.log4j2.threadinfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demonstration {
    private static final Logger logger = LogManager.getLogger(Demonstration.class);

    public static void main(String[] args) {
        logger.info("Starting the system.");

        User james = new User("James");
        User william = new User("William");
        User joseph = new User("Joseph");

        new ApplicationThread(james).start();
        new ApplicationThread(william).start();
        new ApplicationThread(joseph).start();

        james.execute("Added 3x item 1 to cart.");
        james.execute("Added 1x item 2 to cart.");
        james.execute("Removed 1x item 2 from cart.");
        james.execute("Removed 1x item 2 from cart.");
        james.throwError("Can't remove item from cart.");
        james.disconnect();

        william.execute("Added 2x item 2 to cart");
        william.execute("Added 3x item 2 to cart");
        william.execute("Added 4x item 2 to cart");
        william.execute("Payed");
        william.disconnect();

        joseph.execute("Added 1x item 5 to cart");
        joseph.execute("Added 1x item 3 to cart");
        joseph.execute("Added 1x item 7 to cart");
        joseph.execute("Payed");
        joseph.disconnect();
    }
}


