package com.baeldung.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumSearcher {
    private static final Logger LOG = LoggerFactory.getLogger(EnumSearcher.class);

    public static void main(String[] args) {
        EnumSearcher enumSearcher = new EnumSearcher();
        enumSearcher.printWeekdays();
    }

    private void printWeekdays() {
        for (Weekday day: Weekday.values()) {
            LOG.info("Name {}, Value {}, Stringified {}", day.name(), day.getValue(), day);
        }
    }
}
