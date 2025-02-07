package com.baeldung.collections.iterators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class Iterators {

    private static final Logger LOG = LoggerFactory.getLogger(Iterators.class);

    public static int failFast1() {
        ArrayList<Integer> numbers = new ArrayList<>();

        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);

        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            Integer number = iterator.next();
            numbers.add(50);
        }

        return numbers.size();
    }

    public static int failFast2() {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);

        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == 30) {
                // will not throw Exception
                iterator.remove();
            }
        }

        LOG.debug("using iterator's remove method = {}", numbers);

        iterator = numbers.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == 40) {
                // will throw Exception on
                // next call of next() method
                numbers.remove(2);
            }
        }

        return numbers.size();
    }

    public static int failSafe1() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        map.put("First", 10);
        map.put("Second", 20);
        map.put("Third", 30);
        map.put("Fourth", 40);

        Iterator<String> iterator = map.keySet()
            .iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            map.put("Fifth", 50);
        }

        return map.size();
    }

}
