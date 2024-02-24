package com.baeldung.modifyandprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class ModifyAndPrintListElementsUnitTest {

    private final Logger log = LoggerFactory.getLogger(ModifyAndPrintListElementsUnitTest.class);

    @Test
    void whenPrintingInForEach_thenListIsPrinted() {
        List<String> theList = Lists.newArrayList("Kai", "Liam", "Eric", "Kevin");
        theList.forEach(e -> log.info(e));
    }

    @Test
    void whenUsingModifyAndPrintingSeparately_thenListIsModifiedAndPrinted() {
        List<String> theList = Lists.newArrayList("Kai", "Liam", "Eric", "Kevin");
        theList.replaceAll(e -> e.toUpperCase());
        theList.forEach(e -> log.info(e));
        assertEquals(List.of("KAI", "LIAM", "ERIC", "KEVIN"), theList);
    }

    @Test
    void whenPrintingInMap_thenStreamIsModifiedAndPrinted() {
        List<String> theList = List.of("Kai", "Liam", "Eric", "Kevin");
        List<String> newList = theList.stream()
            .map(e -> {
                String newElement = e.toUpperCase();
                log.info(newElement);
                return newElement;
            })
            .filter(e -> e.length() == 4)
            .collect(Collectors.toList());
        assertEquals(List.of("LIAM", "ERIC"), newList);
    }

    @Test
    void whenPrintingInPeek_thenStreamIsModifiedAndPrinted() {
        List<String> theList = List.of("Kai", "Liam", "Eric", "Kevin");
        List<String> newList = theList.stream()
            .map(e -> e.toUpperCase())
            .peek(e -> log.info(e))
            .filter(e -> e.length() == 4)
            .collect(Collectors.toList());
        assertEquals(List.of("LIAM", "ERIC"), newList);
    }
}