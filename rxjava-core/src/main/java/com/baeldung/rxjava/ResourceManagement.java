package com.baeldung.rxjava;

import rx.Observable;

public class ResourceManagement {

    public static void main(String[] args) {

        Observable<Character> values = Observable.using(
          () -> {
              String resource = "MyResource";
              System.out.println("Leased: " + resource);
              return resource;
          },
          r -> Observable.create(o -> {
              for (Character c : r.toCharArray()) {
                  o.onNext(c);
              }
              o.onCompleted();
          }),
          r -> System.out.println("Disposed: " + r)
        );

        values.subscribe(
          System.out::println,
          System.out::println
        );
    }
}

