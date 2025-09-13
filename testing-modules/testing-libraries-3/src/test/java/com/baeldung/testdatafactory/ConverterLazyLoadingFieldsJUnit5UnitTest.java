package com.baeldung.testdatafactory;

import org.junit.jupiter.api.Test;
import uk.org.webcompere.testgadgets.testdatafactory.TestData;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFactory;

import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestDataFactory(path = "testdata")
class ConverterLazyLoadingFieldsJUnit5UnitTest {

    @TestData
    private Supplier<Document> twoParagraphs;

    @TestData("twoParagraphs.txt")
    private Supplier<String> twoParagraphsText;

    @Test
    void givenDocumentAndPlaintextInFiles_whenConvertToText_thenMatches() {
        assertThat(Converter.fromDocument(twoParagraphs.get())).isEqualTo(twoParagraphsText.get());
    }
}
