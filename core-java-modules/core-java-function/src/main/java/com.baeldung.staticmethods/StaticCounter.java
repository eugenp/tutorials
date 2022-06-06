package com.baeldung.staticmethods;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StaticCounter {

    private static int counter = 0;

    public static int incrementCounter() {
        return ++counter;
    }

    public static int getCounterValue() {
        return counter;
    }

}
