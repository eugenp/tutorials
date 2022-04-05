package com.baeldung.switchstatement;

import com.baeldung.loops.LoopsInJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwitchStatement {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwitchStatement.class);

    public String exampleOfIF(String animal) {

        String result;

        if (animal.equals("DOG") || animal.equals("CAT")) {
            result = "domestic animal";
        } else if (animal.equals("TIGER")) {
            result = "wild animal";
        } else {
            result = "unknown animal";
        }
        return result;
    }

    public String exampleOfSwitch(String animal) {

        String result;

        switch (animal) {
        case "DOG":
        case "CAT":
            result = "domestic animal";
            break;
        case "TIGER":
            result = "wild animal";
            break;
        default:
            result = "unknown animal";
            break;
        }
        return result;
    }

    public String forgetBreakInSwitch(String animal) {

        String result;

        switch (animal) {

        case "DOG":
            LOGGER.debug("domestic animal");
            result = "domestic animal";

        default:
            LOGGER.debug("unknown animal");
            result = "unknown animal";

        }
        return result;
    }

    public String constantCaseValue(String animal) {

        String result = "";

        final String dog = "DOG";

        switch (animal) {

        case dog:
            result = "domestic animal";
        }
        return result;
    }

}
