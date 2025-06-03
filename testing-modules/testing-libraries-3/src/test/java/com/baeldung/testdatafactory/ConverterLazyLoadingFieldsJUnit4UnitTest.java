package com.baeldung.testdatafactory;

import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.testgadgets.testdatafactory.TestData;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFieldsRule;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataLoader;

import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConverterLazyLoadingFieldsJUnit4UnitTest {
    @Rule
    public TestDataFieldsRule rule = new TestDataFieldsRule(
      new TestDataLoader().addPath("testdata"));

    @TestData
    private Supplier<Document> twoParagraphs;

    @TestData("twoParagraphs.txt")
    private Supplier<String> twoParagraphsText;

    @Test
    public void givenDocumentAndPlaintextInFiles_whenConvertToText_thenMatches() {
        assertThat(Converter.fromDocument(twoParagraphs.get()))
                .isEqualTo(twoParagraphsText.get());
    }
}
