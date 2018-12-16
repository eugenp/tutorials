package com.baeldung.concurrent.parameter;

public class ParameterizedThreadExample {

    private static void parameterizedThreadLambdaFunction() {
        final String parameter = "123";
        Thread parameterizedThread = new Thread(() -> System.out.println(Thread.currentThread()
            .getName() + " : " + parameter));
        parameterizedThread.start();
    }

    private static void paramtereizedThreadRunnable() {
        Thread parameterizedThread = new Thread(new ParameterizedThread("123"));
        parameterizedThread.start();
    }

    private static void paramterizedThreadAnonymousClass() {
        final String parameter = "123";
        Thread parameterizedThread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread()
                    .getName() + " : " + parameter);
            }
        });

        parameterizedThread.start();
    }

    public static void main(String... args) {
        paramtereizedThreadRunnable();

        paramterizedThreadAnonymousClass();

        parameterizedThreadLambdaFunction();
    }
}
