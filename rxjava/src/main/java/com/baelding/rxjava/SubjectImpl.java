package com.baelding.rxjava;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class SubjectImpl {

    public static final String[] subscriber1 = {""};
    public static final String[] subscriber2 = {""};

    public static String subjectMethod() throws InterruptedException {

        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        Long signal = 500L;
        PublishSubject<String> subject;

        synchronized (signal) {
            subject = PublishSubject.create();
            subject.subscribe(
              (letter) -> {
                  subscriber1[0] += letter;
                  System.out.println("Subscriber 1: " + subscriber1[0]);
                  try {
                      Thread.sleep(500);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  if (letter.equals("c")) {
                      synchronized (signal) {
                          signal.notify();
                      }
                  }
              }
            );
        }

        Observable.from(letters)
          .subscribeOn(Schedulers.computation())
          .subscribe(
            subject::onNext,
            subject::onError,
            () -> {
                System.out.println("Subscriber 1 completed ");
                subject.onCompleted();
                synchronized (signal) {
                    signal.notify();
                }
            }
          );

        synchronized (signal) {
            signal.wait();
            subject.subscribe(
              (letter) -> {
                  subscriber2[0] += letter;
                  System.out.println("Subscriber 2: " + subscriber2[0]);
              },
              subject::onError,
              () -> System.out.println("Subscriber 2 completed ")
            );
        }

        synchronized (signal) {
            signal.wait();
            return subscriber1[0] + subscriber2[0];
        }
    }
}
