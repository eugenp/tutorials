package com.baeldung.testdatafactory;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.testgadgets.testdatafactory.Immutable;
import uk.org.webcompere.testgadgets.testdatafactory.TestData;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataClassRule;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFieldsRule;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataLoader;
import uk.org.webcompere.testgadgets.testdatafactory.TextLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterStaticLoaderJUnit4UnitTest {
    @ClassRule
    public static TestDataClassRule classRule = new TestDataClassRule(
            new TestDataLoader()
                .addLoader(".md", new TextLoader())
                .addPath("testdata"));

    @Rule
    public TestDataFieldsRule rule = new TestDataFieldsRule(classRule.getLoader());

    @TestData("twoParagraphs.txt")
    private static String twoParagraphsTextStatic;

    @TestData("twoParagraphs.txt")
    private String twoParagraphsTextField;

    @TestData("twoParagraphs.json")
    private static Document twoParagraphsStatic;

    @TestData
    private Document twoParagraphs;

    @TestData(value = "twoParagraphs.json", immutable = Immutable.IMMUTABLE)
    private static Document twoParagraphsStaticImmutable;

    @TestData(value = "twoParagraphs.json", immutable = Immutable.IMMUTABLE)
    private Document twoParagraphsImmutable;

    @Test
    public void givenInjectedFieldInSharedLoader_alwaysGetsSameAnswer() {
        assertThat(twoParagraphsTextStatic).isSameAs(twoParagraphsTextField);
    }

    @Test
    public void givenInjectedFieldInSharedLoaderDefaultMutability_alwaysGetsDifferentAnswer() {
        assertThat(twoParagraphsStatic).isNotSameAs(twoParagraphs);
    }

    @Test
    public void givenInjectedFieldInSharedLoaderImmutable_alwaysGetsSameAnswer() {
        assertThat(twoParagraphsStaticImmutable).isSameAs(twoParagraphsImmutable);
    }
}
