package com.baeldung.lambdas;

public class LambdaVariables {

    private String greeting = "Hello";

    public static void main(String[] args) {
        new LambdaVariables().instanceVariableInLambda();
    }

    public void localVariableInLambda() {
        int counter = 1;
        Runnable runnable = () -> System.out.println(counter);

        runnable.run();
        new Thread(runnable).start();
    }

    public void instanceVariableInLambda() {
        Runnable runnable = () -> System.out.println(greeting);

        greeting = "hi";

        new Thread(runnable).start();
    }

    public void parameterLambda(int counter) {
        Runnable runnable = () -> System.out.println(counter);
    }

}
