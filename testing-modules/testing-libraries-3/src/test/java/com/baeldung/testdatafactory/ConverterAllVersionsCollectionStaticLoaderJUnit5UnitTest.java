package com.baeldung.testdatafactory;

import org.junit.jupiter.api.Test;
import uk.org.webcompere.testgadgets.testdatafactory.FileTypeLoader;
import uk.org.webcompere.testgadgets.testdatafactory.Loader;
import uk.org.webcompere.testgadgets.testdatafactory.TestData;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFactory;
import uk.org.webcompere.testgadgets.testdatafactory.TestDataLoader;
import uk.org.webcompere.testgadgets.testdatafactory.TextLoader;

import static org.assertj.core.api.Assertions.assertThat;

@TestDataFactory
class ConverterAllVersionsCollectionStaticLoaderJUnit5UnitTest {
    @Loader
    private static TestDataLoader customLoader = new TestDataLoader()
              .addLoader(".md", new TextLoader())
            .addPath("testdata");

    @TestData("dickens")
    private AllVersions dickens;

    @TestData("shakespeare")
    private AllVersions shakespeare;

    @Test
    void givenMarkdown_thenConvertToDocument() {
        assertThat(Converter.fromMarkdown(dickens.markdown())).isEqualTo(dickens.document());
    }
}
