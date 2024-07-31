package com.baeldung.streams.removeitem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    public void givenAListOf10Items_whenFilteredForQualifiedItems_thenFilteredListContains5Items() {

        final List<Item> filteredList = itemList.stream().filter(item -> item.isQualified())
            .collect(Collectors.toList());

        Assert.assertEquals(5, filteredList.size());
    }

    @Test
    public void givenAListOf10Items_whenOperateAndRemoveQualifiedItemsUsingRemoveIf_thenListContains5Items() {

        final Predicate<Item> isQualified = item -> item.isQualified();
        itemList.stream().filter(isQualified).forEach(item -> item.operate());
        itemList.removeIf(isQualified);

        Assert.assertEquals(5, itemList.size());
    }

    @Test
    public void givenAListOf10Items_whenOperateAndRemoveQualifiedItemsUsingRemoveAll_thenListContains5Items() {

        final List<Item> operatedList = new ArrayList<>();
        itemList.stream().filter(item -> item.isQualified()).forEach(item -> {
            item.operate();
            operatedList.add(item);
        });
        itemList.removeAll(operatedList);

        Assert.assertEquals(5, itemList.size());
    }

    class Item {

        private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

        private final int value;

        public Item(final int value) {

            this.value = value;
        }

        public boolean isQualified() {

            return value % 2 == 0;
        }

        public void operate() {

            logger.info("Even Number: " + this.value);
        }
    }
}