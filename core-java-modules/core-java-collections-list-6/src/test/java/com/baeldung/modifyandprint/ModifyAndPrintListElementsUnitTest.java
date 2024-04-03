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
        theList.forEach(element -> log.info(element));
    }

    @Test
    void whenUsingModifyAndPrintingSeparately_thenListIsModifiedAndPrinted() {
        List<String> theList = Lists.newArrayList("Kai", "Liam", "Eric", "Kevin");
        theList.replaceAll(element -> element.toUpperCase());
        theList.forEach(element -> log.info(element));
        assertEquals(List.of("KAI", "LIAM", "ERIC", "KEVIN"), theList);
    }

    @Test
    void whenPrintingInMap_thenStreamIsModifiedAndPrinted() {
        List<String> theList = List.of("Kai", "Liam", "Eric", "Kevin");
        List<String> newList = theList.stream()
            .map(element -> {
                String newElement = element.toUpperCase();
                log.info(newElement);
                return newElement;
            })
            .collect(Collectors.toList());
        assertEquals(List.of("KAI", "LIAM", "ERIC", "KEVIN"), newList);
    }

    @Test
    void whenPrintingInPeek_thenStreamIsModifiedAndPrinted() {
        List<String> theList = List.of("Kai", "Liam", "Eric", "Kevin");
        List<String> newList = theList.stream()
            .map(element -> element.toUpperCase())
            .peek(element-> log.info(element))
            .collect(Collectors.toList());
        assertEquals(List.of("KAI", "LIAM", "ERIC", "KEVIN"), newList);
    }
}