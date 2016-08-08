package com.baeldung.hystrix;


public class RemoteServiceSimulator {

    public String checkSomething(final long timeout) throws InterruptedException {

        System.out.print(String.format("Waiting %sms. ", timeout));

        // to simulate a real world delay in processing.
        Thread.sleep(timeout);

        return "Done waiting.";
    }
}
