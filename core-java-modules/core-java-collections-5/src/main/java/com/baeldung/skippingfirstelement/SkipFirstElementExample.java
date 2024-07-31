package com.baeldung.skippingfirstelement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SkipFirstElementExample {

    private final List<String> stringList = new ArrayList<>();
    private final Set<String> stringSet = new HashSet<>();
    private final Map<String, String> stringMap = new HashMap<>();

    public SkipFirstElementExample() {
        // Initializing a List
        stringList.add("Monday");
        stringList.add("Tuesday");
        stringList.add("Wednesday");
        stringList.add("Thursday");
        stringList.add("Friday");
        stringList.add("Saturday");
        stringList.add("Sunday");
        // Initializing a Set
        stringSet.add("Monday");
        stringSet.add("Tuesday");
        stringSet.add("Wednesday");
        stringSet.add("Thursday");
        stringSet.add("Friday");
        stringSet.add("Saturday");
        stringSet.add("Sunday");
        // Initializing a Map
        stringMap.put("Monday", "The day when coffee is a life support system.");
        stringMap.put("Tuesday", "The day you realize that Monday's optimism was a lie.");
        stringMap.put("Wednesday", "Hump Day, or as it's known, the 'Is it Friday yet?' day.");
        stringMap.put("Thursday", "The day that says, 'Hold my beer, Friday is coming!'");
        stringMap.put("Friday", "The golden child of the weekdays. The superhero of the workweek.");
        stringMap.put("Saturday", "The day of rest? More like the day of 'What can I binge-watch next?'");
        stringMap.put("Sunday", "The day before you have to adult again.");
    }

    void skippingFirstElementInListWithForLoop(List<String> stringList) {
        for (int i = 1; i < stringList.size(); i++) {
            process(stringList.get(i));
        }
    }

    void skippingFirstElementInListWithWhileLoop(List<String> stringList) {
        final Iterator<String> iterator = stringList.iterator();
        if (iterator.hasNext()) {
            iterator.next();
        }
        while (iterator.hasNext()) {
            process(iterator.next());
        }
    }

    void skippingFirstElementInSetWithWhileLoop(Set<String> stringSet) {
        final Iterator<String> iterator = stringSet.iterator();
        if (iterator.hasNext()) {
            iterator.next();
        }
        while (iterator.hasNext()) {
            process(iterator.next());
        }
    }

    void skippingFirstElementInListWithWhileLoopStoringFirstElement(List<String> stringList) {
        final Iterator<String> iterator = stringList.iterator();
        String firstElement = null;
        if (iterator.hasNext()) {
            firstElement = iterator.next();
        }
        while (iterator.hasNext()) {
            process(iterator.next());
            // additional logic using fistElement
        }
    }

    void skippingFirstElementInMapWithStreamSkip(Map<String, String> stringMap) {
        stringMap.entrySet().stream().skip(1).forEach(this::process);
    }

    void skippingFirstElementInListWithSubList(List<String> stringList) {
        for (final String element : stringList.subList(1, stringList.size())) {
            process(element);
        }
    }

    void skippingFirstElementInListWithForLoopWithAdditionalCheck(List<String> stringList) {
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) {
                // do something else
            } else {
                process(stringList.get(i));
            }
        }
    }

    void skippingFirstElementInListWithWhileLoopWithCounter(List<String> stringList) {
        int counter = 0;
        while (counter < stringList.size()) {
            if (counter != 0) {
                process(stringList.get(counter));
            }
            ++counter;
        }
    }

    void skippingFirstElementInListWithReduce(List<String> stringList) {
        stringList.stream().reduce((skip, element) -> {
            process(element);
            return element;
        });
    }

    protected void process(String string) {
        System.out.println(string);
    }
    protected void process(Entry<String, String> mapEntry) {
        System.out.println(mapEntry);
    }
}