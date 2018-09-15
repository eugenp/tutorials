package com.baeldung.switchstatement;

public class SwitchStatement {

    public String exampleOfIF() {

        String result;
        String animal = "DOG";
        if (animal.equals("DOG") || animal.equals("CAT")) {
            result = "domestic animal";
        } else if (animal.equals("TIGER")) {
            result = "wild animal";
        } else {
            result = "unknown animal";
        }
        return result;
    }

    public String exampleOfSwitch() {

        String result;
        String animal = "DOG";
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

    public String forgetBreakInSwitch() {

        String result;
        String animal = "DOG";
        switch (animal) {
        case "DOG":
        case "CAT":
            System.out.println("domestic animal");
            result = "domestic animal";

        case "TIGER":
            result = "wild animal";
            System.out.println("wild animal");
        default:
            result = "unknown animal";
            System.out.println("unknown animal");
        }
        return result;
    }

    public String nullValueAsSwitchArgument() {

        String result;
        String animal = null;
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

    public String compileTimeConstantsForCase() {
        final String cat = "CAT";
        // String cat="CAT";

        String result;
        String animal = "DOG";
        switch (animal) {
        case "DOG":
        case cat:
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

    public boolean compareStringsByEqualityOperator() {

        String animal = new String("TIGER");
        final String tiger = "TIGER";
        return animal == tiger;
    }

    public String compareStringsByEquals() {
        String result;
        String animal = new String("TIGER");
        final String tiger = "TIGER";

        switch (animal) {
        case "DOG":
        case "CAT":
            result = "domestic animal";
            break;
        case tiger:
            result = "wild animal";
            break;
        default:
            result = "unknown animal";
            break;
        }
        return result;
    }
}
