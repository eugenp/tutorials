package com.baeldung.univocity;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class ParsingServiceUnitTest {

    @Test
    public void givenCsvFile_thenParsedResultsShouldBeReturned() {
        ParsingService parsingService = new ParsingService();
        List<String[]> productData = parsingService.parseCsvFile("productList.csv");
        assertEquals(3, productData.size());
    }
}
