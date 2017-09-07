package com.baeldung.rxjava;

import org.junit.Test;
import rx.Observable;

import static junit.framework.Assert.assertTrue;

public class ResourceManagementTest {

    @Test
    public void givenResource_whenUsingOberservable_thenCreatePrintDisposeResource() throws InterruptedException {

        final String[] result = {""};

        Observable<Character> values = Observable.using(
                //a factory function that creates a disposable resource
                () -> {
                    String resource = "MyResource";
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
                v -> result[0] += v,
                e -> result[0] += e
        );

        assertTrue(result[0].equals("MyResource"));

    }
}
