package com.baeldung.list.ignorecase;

import java.util.ArrayList;
import java.util.Collection;

public class IgnoreCaseStringList extends ArrayList<String> {

    public IgnoreCaseStringList() {

    }

    public IgnoreCaseStringList(Collection<? extends String> c) {
        super(c);
    }

    @Override
    public boolean contains(Object o) {
        String searchStr = (String) o;
        // Using Stream API:
        // return this.stream().anyMatch(searchStr::equalsIgnoreCase);
        for (String s : this) {
            if (searchStr.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

}
