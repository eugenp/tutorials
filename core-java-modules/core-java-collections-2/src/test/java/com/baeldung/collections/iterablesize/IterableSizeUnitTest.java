package com.baeldung.collections.iterablesize;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

class IterableSizeUnitTest {

    private final List<String> list = Lists.newArrayList("Apple", "Orange", "Banana");

    private Iterable data;

    @Test
    void whenUsingJava7_iterableOfCollectionType_thenCorrectSize() {

        final int size = IterableSize.sizeUsingJava7(list);

        assertEquals(3, size);
    }

    @Test
    void whenUsingJava7_iterableNotOfCollectionType_thenCorrect() {

        final SQLException exception = new SQLException();
        exception.setNextException(new SQLException());
        final int size = IterableSize.sizeUsingJava7(exception);

        assertEquals(2, size);
    }

    @Test
    void whenUsingJava8_thenCorrect() {

        final long size = IterableSize.sizeUsingJava8(list);

        assertEquals(3, size);
    }

    @Test
    void whenUsingApacheCollections_thenCorrect() {

        final int size = IterableSize.sizeUsingApacheCollections(list);

        assertEquals(3, size);
    }

    @Test
    void whenUsingGoogleGuava_thenCorrect() {

        final int size = IterableSize.sizeUsingGoogleGuava(list);

        assertEquals(3, size);
    }
}