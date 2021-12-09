package com.baeldung.object.type;

import java.util.List;

public class TreeSorter {
    protected Tree sortTree(String name) {

        final List<String> deciduous = List.of("Beech", "Birch", "Ash", "Whitebeam", "Hornbeam", "Hazel & Willow");
        final List<String> evergreen = List.of("Cedar", "Holly", "Laurel", "Olive", "Pine");

        if (deciduous.contains(name)) {
            return new Deciduous(name);
        } else if (evergreen.contains(name)) {
            return new Evergreen(name);
        } else {
            throw new RuntimeException("Tree could not be classified");
        }
    }
}
