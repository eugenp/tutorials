package com.baeldung.testdatafactory;

import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.testgadgets.testdatafactory.TestData;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFieldsRule;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataLoader;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConverterTestFactoryFieldsJUnit4UnitTest {
    @Rule
    public TestDataFieldsRule rule = new TestDataFieldsRule(
      new TestDataLoader().addPath("testdata"));

    @TestData
    private Document twoParagraphs;

    @TestData("twoParagraphs.txt")
    private String twoParagraphsText;

    @Test
    public void givenDocumentAndPlaintextInFiles_whenConvertToText_thenMatches() {
        assertThat(Converter.fromDocument(twoParagraphs)).isEqualTo(twoParagraphsText);
    }
}
