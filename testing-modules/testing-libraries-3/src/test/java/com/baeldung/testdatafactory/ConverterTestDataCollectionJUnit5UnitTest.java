package com.baeldung.testdatafactory;

import org.junit.jupiter.api.Test;
import uk.org.webcompere.testgadgets.testdatafactory.TestData;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestDataFactory(path = "testdata")
class ConverterTestDataCollectionJUnit5UnitTest {

    @TestData
    private TwoParagraphsCollection collection;

    @Test
    void givenDocumentAndPlaintextInFiles_whenConvertToText_thenMatches() {
        assertThat(Converter.fromDocument(collection.twoParagraphs()))
                .isEqualTo(collection.twoParagraphsText());
    }

    @Test
    void givenInjectedCollection_whenConvertToText_thenMatches(@TestData TwoParagraphsCollection collection) {
        assertThat(Converter.fromDocument(collection.twoParagraphs()))
                .isEqualTo(collection.twoParagraphsText());
    }
}
