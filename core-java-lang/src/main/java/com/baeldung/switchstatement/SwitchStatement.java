package com.baeldung.switchstatement;

public class SwitchStatement {

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
            System.out.println("domestic animal");
            result = "domestic animal";

        default:
            System.out.println("unknown animal");
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
