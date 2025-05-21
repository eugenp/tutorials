package com.baeldung.testdatafactory;

import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.testgadgets.testdatafactory.TestData;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFieldsRule;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataLoader;
import uk.org.webcompere.testgadgets.testdatafactory.TextLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterAllVersionsCollectionJUnit4UnitTest {
    @Rule
    public TestDataFieldsRule rule = new TestDataFieldsRule(
            new TestDataLoader()
              .addLoader(".md", new TextLoader())
              .addPath("testdata"));

    @TestData("dickens")
    private AllVersions dickens;

    @TestData("shakespeare")
    private AllVersions shakespeare;

    @Test
    public void markdownToDocumentDickens() {
        assertThat(Converter.fromMarkdown(dickens.markdown())).isEqualTo(dickens.document());
    }

    @Test
    public void textToDocumentDickens() {
        Document document = dickens.document();
        document.getParagraphs().get(0).setStyle(Paragraph.Style.NORMAL);
        assertThat(Converter.fromText(dickens.text())).isEqualTo(document);
    }

    @Test
    public void documentToMarkdownDickens() {
        assertThat(Converter.toMarkdown(dickens.document())).isEqualTo(dickens.markdown());
    }

    @Test
    public void markdownToDocumentShakespeare() {
        assertThat(Converter.fromMarkdown(shakespeare.markdown())).isEqualTo(shakespeare.document());
    }

    @Test
    public void textToDocumentShakespeare() {
        Document document = shakespeare.document();
        document.getParagraphs().get(0).setStyle(Paragraph.Style.NORMAL);
        document.getParagraphs().get(1).setStyle(Paragraph.Style.NORMAL);
        assertThat(Converter.fromText(shakespeare.text())).isEqualTo(document);
    }

    @Test
    public void documentToMarkdownShakespeare() {
        assertThat(Converter.toMarkdown(shakespeare.document())).isEqualTo(shakespeare.markdown());
    }
}
