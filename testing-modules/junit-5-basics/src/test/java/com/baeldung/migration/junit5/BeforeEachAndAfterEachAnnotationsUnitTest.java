package com.baeldung.migration.junit5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeforeEachAndAfterEachAnnotationsUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(BeforeEachAndAfterEachAnnotationsUnitTest.class);

    private List<String> list;

    @BeforeEach
    void init() {
        LOG.debug("startup");
        list = new ArrayList<>(Arrays.asList("test1", "test2"));
    }

    @AfterEach
    void teardown() {
        LOG.debug("teardown");
        list.clear();
    }

    @Test
    void whenCheckingListSize_thenSizeEqualsToInit() {
        LOG.debug("executing test");
        assertEquals(2, list.size());

        list.add("another test");
    }

    @Test
    public void whenCheckingListSizeAgain_thenSizeEqualsToInit() {
        LOG.debug("executing another test");
        assertEquals(2, list.size());

        list.add("yet another test");
    }
}
