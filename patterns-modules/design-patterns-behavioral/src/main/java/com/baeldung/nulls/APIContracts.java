package com.baeldung.nulls;

public class APIContracts {

    /**
     * Prints the value of {@code param} if not null. Prints {@code null} otherwise.
     *
     * @param param
     */
    public void print(Object param) {
        System.out.println("Printing " + param);
    }

    /**
     * @return non null result
     * @throws Exception - if result is null
     */
    public Object process() throws Exception {
        Object result = doSomething();
        if (result == null) {
            throw new Exception("Processing fail. Got a null response");
        } else {
            return result;
        }
    }

    private Object doSomething() {
        return null;
    }
}
