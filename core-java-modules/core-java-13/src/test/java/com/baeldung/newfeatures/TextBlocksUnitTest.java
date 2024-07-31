package com.baeldung.newfeatures;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TextBlocksUnitTest {

    private static final String JSON_STRING = "{\r\n" + "\"name\" : \"Baeldung\",\r\n" + "\"website\" : \"https://www.%s.com/\"\r\n" + "}";

    private static final String TEXT_BLOCK_JSON = """
            {
            "name" : "Baeldung",
            "website" : "https://www.%s.com/"
            }
        """;

    @Test
    public void whenTextBlocks_thenStringOperationsWork() {

        assertThat(TEXT_BLOCK_JSON.contains("Baeldung")).isTrue();
        assertThat(TEXT_BLOCK_JSON.indexOf("www")).isGreaterThan(0);
        assertThat(TEXT_BLOCK_JSON.length()).isGreaterThan(0);

    }

    @Test
    public void whenTextBlocks_thenFormattedWorksAsFormat() {
        assertThat(TEXT_BLOCK_JSON.formatted("baeldung")
            .contains("www.baeldung.com")).isTrue();

        assertThat(String.format(JSON_STRING, "baeldung")
            .contains("www.baeldung.com")).isTrue();

    }
   
}
