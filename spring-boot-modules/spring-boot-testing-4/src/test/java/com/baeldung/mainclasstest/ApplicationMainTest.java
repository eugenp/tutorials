package com.baeldung.mainclasstest;

import org.junit.jupiter.api.Test;

import com.baeldung.Application;

public class ApplicationMainTest {

    @Test
    public void testMain() {
        Application.main(new String[] {});
    }
}