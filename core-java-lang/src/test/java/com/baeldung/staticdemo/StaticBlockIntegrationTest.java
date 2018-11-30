package com.baeldung.staticdemo;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class StaticBlockIntegrationTest {

    @Test
    public void whenAddedListElementsThroughStaticBlock_thenEnsureCorrectOrder() {
        List<String> actualList = StaticBlock.getRanks();
        assertThat(actualList, contains("Lieutenant", "Captain", "Major", "Colonel", "General"));
    }
}
