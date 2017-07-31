package org.baeldung.mockito;

import java.util.AbstractList;

class MyList extends AbstractList<String> {

    @Override
    public String get(final int index) {
        return null;
    }

    @Override
    public int size() {
        return 1;
    }

}
