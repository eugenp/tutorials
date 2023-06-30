package com.baeldung.sortdatestrings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

public class SortDateStringsUnitTest {
    private List<String> dtStrings = null;
    // @formatter:off
    private static final List<String> EXPECTED = Lists.newArrayList(
        "01/20/2013 06:16",
        "01/20/2013 10:48",
        "01/21/2013 10:41",
        "01/21/2013 16:37",
        "01/21/2013 17:16",
        "01/21/2013 17:19",
        "01/22/2013 06:19",
        "01/22/2013 15:13"
    );
    // @formatter:on

    @BeforeEach
    void resetInput() {
        // @formatter:off
        dtStrings = Lists.newArrayList(
          "01/21/2013 10:41",
          "01/20/2013 10:48",
          "01/22/2013 15:13",
          "01/21/2013 16:37",
          "01/21/2013 17:16",
          "01/21/2013 17:19",
          "01/20/2013 06:16",
          "01/22/2013 06:19"
        );
        // @formatter:on

    }

    @Test
    void whenUsingCustomComparator_thenGetExpectedResult() {
        DateFormat dfm = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Collections.sort(dtStrings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                try {
                    return dfm.parse(o1)
                      .compareTo(dfm.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        assertEquals(EXPECTED, dtStrings);
    }

    @Test
    void whenUsingListSort_thenGetExpectedResult() {
        DateTimeFormatter dfm = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        dtStrings.sort(Comparator.comparing(s -> LocalDateTime.parse(s, dfm)));
        assertEquals(EXPECTED, dtStrings);
    }

    @Test
    void whenUsingStreamToNewList_thenGetExpectedResult() {
        DateTimeFormatter dfm = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        List<String> sortedList = dtStrings.stream()
          .map(s -> LocalDateTime.parse(s, dfm))
          .sorted()
          .map(dfm::format)
          .collect(Collectors.toList());
        assertEquals(EXPECTED, sortedList);
    }

    @Test
    void whenUsingTreeMap_thenGetExpectedResult() {
        DateTimeFormatter dfm = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        Map<LocalDateTime, String> dateFormatMap = new TreeMap<>();
        dtStrings.forEach(s -> dateFormatMap.put(LocalDateTime.parse(s, dfm), s));
        List<String> result = new ArrayList<>(dateFormatMap.values());
        assertEquals(EXPECTED, result);
    }
}