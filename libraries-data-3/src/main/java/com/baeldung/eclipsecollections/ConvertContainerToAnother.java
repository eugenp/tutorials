package com.baeldung.eclipsecollections;

import java.util.List;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class ConvertContainerToAnother {

    @SuppressWarnings("rawtypes")
    public static List convertToList() {
        UnifiedSet<String> cars = new UnifiedSet<>();

        cars.add("Toyota");
        cars.add("Mercedes");
        cars.add("Volkswagen");

        return cars.toList();
    }
}
