package com.baeldung.migration.junit4;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnit4.class)
public class BeforeAndAfterAnnotationsUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(BeforeAndAfterAnnotationsUnitTest.class);

    private List<String> list;

    @Before
    public void init() {
        LOG.debug("startup");
        list = new ArrayList<>(Arrays.asList("test1", "test2"));
    }

    @After
    public void teardown() {
        LOG.debug("teardown");
        list.clear();
    }

    @Test
    public void whenCheckingListSize_thenSizeEqualsToInit() {
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
