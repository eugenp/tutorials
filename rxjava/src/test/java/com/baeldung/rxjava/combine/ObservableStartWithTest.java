package com.baeldung.rxjava.combine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import rx.Observable;

public class ObservableStartWithTest {

    @Test
    public void testStartWith() {
        StringBuffer buffer = new StringBuffer();
        Observable.from(new String[] { "RxJava", "Observables" })
            .startWith("Buzzwords of Reactive Programming")
            .subscribe(data -> {
                buffer.append(data).append(" ");
            });
        
        assertEquals("Buzzwords of Reactive Programming RxJava Observables", buffer.toString().trim());
    }
}
