package com.baeldung.noexception;

public class CustomExceptionHandling {

    public static void main(String[] args) {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        customExceptionHandler.run(() -> throwSomething());
    }

    private static void throwSomething() {
        // String testString = "foo";
        // char testChar = testString.charAt(5);

        throw new Error("This is very bad.");
    }
}
