package com.baeldung.vavr.future;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import io.vavr.concurrent.Future;

public class Tester {

    @Test
    public void start() {
        Future<Integer> resultFuture = Future.of(() -> addOne(4));
        Integer result = resultFuture.get();

        assertEquals(5, (int) result);
    }

    public Integer addOne(Integer num) {
        return num + 1;
    }

}
