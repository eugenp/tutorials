package com.baeldung.singleton;

public class SingletonDemo {

    public int sum(int a, int b) {
        int result = a + b;
        Logger logger = Logger.getInstance();
        logger.log("The sum is " + result);
        return result;
    }

}