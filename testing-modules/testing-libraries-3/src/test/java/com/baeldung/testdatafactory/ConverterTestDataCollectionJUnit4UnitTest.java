package com.baeldung.testdatafactory;

import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.testgadgets.testdatafactory.TestData;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataCollection;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFieldsRule;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataLoader;

import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConverterTestDataCollectionJUnit4UnitTest {
    @Rule
    public TestDataFieldsRule rule = new TestDataFieldsRule(
      new TestDataLoader().addPath("testdata"));

    @TestData
    private TwoParagraphsCollection collection;

    @Test
    public void givenDocumentAndPlaintextInFiles_whenConvertToText_thenMatches() {
        assertThat(Converter.fromDocument(collection.twoParagraphs()))
                .isEqualTo(collection.twoParagraphsText());
    }
}
