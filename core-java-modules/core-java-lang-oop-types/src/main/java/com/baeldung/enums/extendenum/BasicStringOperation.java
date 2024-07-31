package com.baeldung.enums.extendenum;

public enum BasicStringOperation implements StringOperation {
    TRIM("Removing leading and trailing spaces.") {
        @Override
        public String apply(String input) {
            return input.trim();
        }
    },
    TO_UPPER("Changing all characters into upper case.") {
        @Override
        public String apply(String input) {
            return input.toUpperCase();
        }
    },
    REVERSE("Reversing the given string.") {
        @Override
        public String apply(String input) {
            return new StringBuilder(input).reverse().toString();
        }
    };

    private String description;

    public String getDescription() {
        return description;
    }

    BasicStringOperation(String description) {
        this.description = description;
    }
}

