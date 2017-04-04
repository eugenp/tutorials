package com.baeldung.java9.reactive;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;

public class ReactiveStreams {

    private SubmissionPublisher<String> publisher;
    private TestSubscriber<String> subscriber;

    public ReactiveStreams() {
        this.publisher = new SubmissionPublisher<String>(ForkJoinPool.commonPool(), 1);
        this.subscriber = new TestSubscriber<String>();
    }

    public int countStream(ArrayList<String> myList) {
        publisher.subscribe(subscriber);

        myList.stream()
            .forEach(item -> publisher.submit(item));
        publisher.close();

        do {
            // wait for subscribers to complete all processing.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } while (!subscriber.isCompleted());

        return subscriber.getCounter();
    }

    public static void main(String[] args) {
        ArrayList<String> myList = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            myList.add("item" + i);
        }

        ReactiveStreams demo = new ReactiveStreams();
        demo.countStream(myList);
    }

}