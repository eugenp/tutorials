package com.baeldung.testdatafactory;

import org.junit.jupiter.api.Test;
import uk.org.webcompere.testgadgets.testdatafactory.TestData;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestDataFactory(path = "testdata")
class ConverterTestFactoryFieldsJUnit5UnitTest {

    @TestData
    private Document twoParagraphs;

    @TestData("twoParagraphs.txt")
    private String twoParagraphsText;

    @Test
    void givenDocumentAndPlaintextInFiles_whenConvertToText_thenMatches() {
        assertThat(Converter.fromDocument(twoParagraphs)).isEqualTo(twoParagraphsText);
    }

    @Test
    void givenInjectedFiles_whenConvertToText_thenMatches(
            @TestData("twoParagraphs.json") Document twoParagraphs,
            @TestData("twoParagraphs.txt") String twoParagraphsText) {
        assertThat(Converter.fromDocument(twoParagraphs)).isEqualTo(twoParagraphsText);
    }
}
