package com.baeldung.mockito.stubbing;

import java.util.AbstractList;

public class BaeldungList extends AbstractList<String> {

    @Override
    public String get(final int index) {
        return null;
    }

    @Override
    public void add(int index, String element) {
        // no-op
    }

    @Override
    public int size() {
        return 0;
    }
}
