package com.baeldung.testdatafactory;

import uk.org.webcompere.testgadgets.testdatafactory.TestData;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataCollection;

@TestDataCollection
public interface AllVersions {
    @TestData("text.json")
    Document document();

    @TestData("text.md")
    String markdown();

    @TestData("text.txt")
    String text();
}
