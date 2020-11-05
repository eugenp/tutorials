package com.baeldung.iterationcounter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class ForLoopIterationCounter {
    private final static Logger LOGGER = Logger.getLogger(ForLoopIterationCounter.class.getName());
    private static final List<String> COLORS = Arrays.asList("red", "blue", "yellow", "green", "orange", "purple", "brown", "black");

    public void printFirstFive() {
        for (int i = 0; i <= 5; i++) {
            LOGGER.info("Value:" + COLORS.get(i) + ", iteration: " + i);
        }
    }

    public void printFirstFiveForEach() {
        int counter = 0;
        for (String element : COLORS) {
            counter++;
            if (counter <= 5) {
                LOGGER.info("Value:" + element + ", iteration: " + counter);
            }
        }
    }

    @FunctionalInterface
    interface LoopWithIndexFunction<T> {
        void accept(T t, int i);
    }

    public static <T> void iterate(Collection<T> collection, LoopWithIndexFunction<T> consumer) {
        int index = 0;
        for (T object : collection) {
            consumer.accept(object, index++);
        }
    }

    public void printFirstFiveElementsUsingLambda(List<String> list) {
        iterate(list, (element, i) -> {
            if (i <= 5) {
                LOGGER.info("Value:" + element + ", iteration: " + (i + 1));
            }
        });
    }
}
