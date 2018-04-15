package com.baeldung.migration.junit5;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnitPlatform.class)
public class BeforeEachAndAfterEachAnnotationsTest {

    private static final Logger LOG = LoggerFactory.getLogger(BeforeEachAndAfterEachAnnotationsTest.class);
    
    private List<String> list;
    
    @BeforeEach 
    public void init() {
        LOG.info("startup");

        list = new ArrayList<>();
        list.add("test1");
        list.add("test2");
    }

    @AfterEach
    public void finalize() {
        LOG.info("finalize");

        list.clear();
    }

    @Test
    public void whenCheckingListSizeAtBeginning_ThenSizeEqualsToStartupSize() {
        LOG.info("executing test");
        assertEquals(2, list.size());

        list.add("another test");
    }

    @Test
    public void whenCheckingListSizeAtBeginningAgain_ThenSizeEqualsToStartupSize() {
        LOG.info("executing another test");
        assertEquals(2, list.size());

        list.add("yet another test");
    }
}
