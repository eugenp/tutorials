package com.baeldung.testdatafactory;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ConverterJavaFactoryUnitTest {

    @Test
    void givenDocument_whenConvertToText_thenMatches() {
        Document source = TestDataFactory.twoParagraphsAsDocument();

        String asPlaintext = TestDataFactory.twoParagraphs();

        assertThat(Converter.fromDocument(source)).isEqualTo(asPlaintext);
    }

    @Test
    void givenDocumentAndPlaintextInFiles_whenConvertToText_thenMatches() throws IOException {
        Document source = TestDataFilesFactory.twoParagraphsAsDocument();

        String asPlaintext = TestDataFilesFactory.twoParagraphs();

        assertThat(Converter.fromDocument(source)).isEqualTo(asPlaintext);
    }
}
