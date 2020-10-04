package com.baeldung.genericarrays;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CollectionsListUnitTest {

    @Test
    public void givenCollectionList_whenAddAllStrings_thenShouldAddSuccessfully() {
        CollectionsList<CharSequence> collectionsList = new CollectionsList<>();
        List<String> itemStrings = new ArrayList<>();
        itemStrings.add("baeldung");

        collectionsList.addAll(itemStrings);
        assertEquals("baeldung", collectionsList.get(0));
    }
}