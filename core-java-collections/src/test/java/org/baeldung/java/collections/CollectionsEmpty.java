package org.baeldung.java.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

class CollectionsEmpty {

    @Test
    public void givenArrayList_whenAddingElement_addsNewElement() {
        ArrayList<String> mutableList = new ArrayList<>();
        mutableList.add("test");

        Assert.assertEquals(mutableList.size(), 1);
        Assert.assertEquals(mutableList.get(0), "test");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenCollectionsEmptyList_whenAddingElement_throwsUnsupportedOperationException() {
        List<String> immutableList = Collections.emptyList();
        immutableList.add("test");
    }

}
