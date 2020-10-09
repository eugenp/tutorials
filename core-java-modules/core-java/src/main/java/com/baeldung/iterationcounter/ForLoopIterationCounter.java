package com.baeldung.iterationcounter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ForLoopIterationCounter {
    private final static Logger LOGGER = Logger.getLogger(ForLoopIterationCounter.class.getName());

    public int forEachCounter(List<String> list) {
        int counter = 0;
        for (String element : list) {
            counter++;
            LOGGER.info("Value:" + element + ", iteration: " + counter);
        }
        return counter;
    }

    public int forEachLambdaAtomicCounter(List<String> list) {
        AtomicInteger counter = new AtomicInteger();
        list.forEach(element -> {
            int index = counter.incrementAndGet();
            LOGGER.info("Value:" + element + ", iteration: " + index);
        });
        return counter.intValue();
    }

    public void forEachLambda(List<String> list) {
        forEachWithFunctionalInterface(list, (element, i) -> LOGGER.info("Value:" + element + ", iteration: " + (i + 1)));
    }

    @FunctionalInterface
    public interface loopWithIndex<T> {
        void accept(T t, int i);
    }

    public static <T> void forEachWithFunctionalInterface(Collection<T> collection,
                                                          loopWithIndex<T> consumer) {
        int index = 0;
        for (T object : collection) {
            consumer.accept(object, index++);
        }
    }
}
