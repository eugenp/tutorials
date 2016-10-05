package com.baeldung.wicket.examples;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

public class TestHomePage {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new ExamplesApplication());
    }

    @Test
    public void whenPageInvoked_thanRenderedOK() {
        //start and render the test page
        tester.startPage(Examples.class);

        //assert rendered page class
        tester.assertRenderedPage(Examples.class);
    }
}
