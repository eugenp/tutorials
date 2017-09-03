package com.baelding.rxjava;


import rx.Observable;

public class ResourceManagement {


    public static void main(String[] args) {

        Observable<Character> values = Observable.using(
                //a factory function that creates a disposable resource
                () -> {
                    String resource = "MyResource";
                    System.out.println("Leased: " + resource);
                    return resource;
                },
                //a factory function that creates an Observable
                (resource) -> {
                    return Observable.create(o -> {
                        for (Character c : resource.toCharArray())
                            o.onNext(c);
                        o.onCompleted();
                    });
                },
                //a function that disposes of the resource
                (resource) -> System.out.println("Disposed: " + resource)
        );

        values.subscribe(
                v -> System.out.println(v),
                e -> System.out.println(e)
        );

    }
}

