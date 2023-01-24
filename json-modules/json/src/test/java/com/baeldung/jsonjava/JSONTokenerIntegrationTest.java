package com.baeldung.jsonjava;

import org.json.JSONTokener;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JSONTokenerIntegrationTest {

    @Test
    public void givenString_convertItToJSONTokens() {
        String str = "Sample String";
        JSONTokener jt = new JSONTokener(str);

        char[] expectedTokens = str.toCharArray();
        int index = 0;

        while (jt.more()) {
            assertThat(jt.next()).isEqualTo(expectedTokens[index++]);
        }
    }
}
