package com.baeldung.testdatafactory;

import uk.org.webcompere.testgadgets.testdatafactory.TestData;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataCollection;

@TestDataCollection
public interface TwoParagraphsCollection {
    @TestData
    Document twoParagraphs();

    @TestData("twoParagraphs.txt")
    String twoParagraphsText();
}
