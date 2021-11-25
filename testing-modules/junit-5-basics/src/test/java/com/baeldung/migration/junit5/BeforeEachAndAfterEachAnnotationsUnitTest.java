package com.baeldung.migration.junit5;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnitPlatform.class)
public class BeforeEachAndAfterEachAnnotationsUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(BeforeEachAndAfterEachAnnotationsUnitTest.class);
    
    private List<String> list;
    
    @BeforeEach 
    public void init() {
        LOG.debug("startup");
        list = new ArrayList<>(Arrays.asList("test1", "test2"));
    }

    @AfterEach
    public void teardown() {
        LOG.debug("teardown");
        list.clear();
    }

    @Test
    public void whenCheckingListSize_ThenSizeEqualsToInit() {
        LOG.debug("executing test");
        assertEquals(2, list.size());

        list.add("another test");
    }

    @Test
    public void whenCheckingListSizeAgain_ThenSizeEqualsToInit() {
        LOG.debug("executing another test");
        assertEquals(2, list.size());

        list.add("yet another test");
    }
}
