package org.baeldung.java.collections;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StreamOperateAndRemoveUnitTest {

    private List<Item> itemList;

    @Before
    public void setup() {

        itemList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemList.add(new Item(i));
        }
    }

    @Test
    public void givenAList_operateUsingStreamsAndRemoveUsingRemoveIf_thenCorrect() {

        itemList.stream().filter(item -> item.isQualified()).forEach(item -> item.operate());
        itemList.removeIf(item -> item.isQualified());

        Assert.assertEquals(5, itemList.size());
    }

    @Test
    public void givenAList_operateUsingStreamsAndRemoveUsingRemoveAll_thenCorrect() {

        final List<Item> operatedList = new ArrayList<>();
        itemList.stream().filter(item -> item.isQualified()).forEach(item -> {
            item.operate();
            operatedList.add(item);
        });
        itemList.removeAll(operatedList);

        Assert.assertEquals(5, itemList.size());
    }
}

class Item {

    private final int value;

    public Item(final int value) {

        this.value = value;
    }

    public boolean isQualified() {

        return value % 2 == 0;
    }

    public void operate() {

        System.out.println("Even Number: " + this.value);
    }
}
