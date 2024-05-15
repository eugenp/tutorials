package com.baeldung.staticmodifier;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class StaticBlockDemoUnitTest {

    @Test
    public void whenAddedListElementsThroughStaticBlock_thenEnsureCorrectOrder() {
        List<String> actualList = StaticBlockDemo.getRanks();
        assertThat(actualList, contains("Lieutenant", "Captain", "Major", "Colonel", "General"));
    }
}
