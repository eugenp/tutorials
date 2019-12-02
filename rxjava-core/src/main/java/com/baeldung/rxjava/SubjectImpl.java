package com.baeldung.rxjava;

import rx.Observer;
import rx.subjects.PublishSubject;

public class SubjectImpl {

    static Integer subscriber1 = 0;
    static Integer subscriber2 = 0;

    private static Integer subjectMethod() {
        PublishSubject<Integer> subject = PublishSubject.create();

        subject.subscribe(getFirstObserver());

        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);

        subject.subscribe(getSecondObserver());

        subject.onNext(4);
        subject.onCompleted();
        return subscriber1 + subscriber2;
    }


    static Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {

            @Override
            public void onNext(Integer value) {
                subscriber1 += value;
                System.out.println("Subscriber1: " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
            }

            @Override
            public void onCompleted() {
                System.out.println("Subscriber1 completed");
            }
        };
    }

    static Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {

            @Override
            public void onNext(Integer value) {
                subscriber2 += value;
                System.out.println("Subscriber2: " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
            }

            @Override
            public void onCompleted() {
                System.out.println("Subscriber2 completed");
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(subjectMethod());
    }
}
