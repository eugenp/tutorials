package com.baeldung.newfeatures;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TextBlocksUnitTest {

    @SuppressWarnings("preview")
    private static final String TEXT_BLOCK_JSON = """
            {
            "name" : "Baeldung",
            "website" : "https://www.baeldung.com/"
            }
        """;

    @Test
    public void whenTextBlocks_thenStringOperationsWork() {

        assertThat(TEXT_BLOCK_JSON.contains("Baeldung")).isTrue();
        assertThat(TEXT_BLOCK_JSON.indexOf("www")).isGreaterThan(0);
        assertThat(TEXT_BLOCK_JSON.length()).isGreaterThan(0);

    }
}
