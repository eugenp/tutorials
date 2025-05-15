package com.baeldung.testdatafactory;

import org.junit.jupiter.api.Test;
import uk.org.webcompere.testgadgets.testdatafactory.FileTypeLoader;
import uk.org.webcompere.testgadgets.testdatafactory.Loader;
import uk.org.webcompere.testgadgets.testdatafactory.TestData;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFactory;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataLoader;
import uk.org.webcompere.testgadgets.testdatafactory.TextLoader;

import static org.assertj.core.api.Assertions.assertThat;

@TestDataFactory(
    loaders = { @FileTypeLoader(extension = ".md", loadedBy = TextLoader.class) },
    path = "testdata")
class ConverterAllVersionsCollectionJUnit5UnitTest {
    @TestData("dickens")
    private AllVersions dickens;

    @TestData("shakespeare")
    private AllVersions shakespeare;

    @Loader
    private TestDataLoader loader;

    @Test
    void markdownToDocumentDickens() {
        assertThat(Converter.fromMarkdown(dickens.markdown())).isEqualTo(dickens.document());
    }

    @Test
    void textToDocumentDickens() {
        Document document = dickens.document();
        document.getParagraphs().get(0).setStyle(Paragraph.Style.NORMAL);
        assertThat(Converter.fromText(dickens.text())).isEqualTo(document);
    }

    @Test
    void documentToMarkdownDickens() {
        assertThat(Converter.toMarkdown(dickens.document())).isEqualTo(dickens.markdown());
    }

    @Test
    void markdownToDocumentShakespeare() {
        assertThat(Converter.fromMarkdown(shakespeare.markdown())).isEqualTo(shakespeare.document());
    }

    @Test
    void textToDocumentShakespeare() {
        Document document = shakespeare.document();
        document.getParagraphs().get(0).setStyle(Paragraph.Style.NORMAL);
        document.getParagraphs().get(1).setStyle(Paragraph.Style.NORMAL);
        assertThat(Converter.fromText(shakespeare.text())).isEqualTo(document);
    }

    @Test
    void documentToMarkdownShakespeare() {
        assertThat(Converter.toMarkdown(shakespeare.document())).isEqualTo(shakespeare.markdown());
    }
}
