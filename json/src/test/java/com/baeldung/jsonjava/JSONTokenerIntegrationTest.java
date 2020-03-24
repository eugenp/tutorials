package com.baeldung.jsonjava;

import static org.junit.Assert.assertEquals;

import org.json.JSONTokener;
import org.junit.Test;

public class JSONTokenerIntegrationTest {
    @Test
    public void givenString_convertItToJSONTokens() {
        String str = "Sample String";
        JSONTokener jt = new JSONTokener(str);
        
        char[] expectedTokens = str.toCharArray();
        int index = 0;
        
        while(jt.more()) {
            assertEquals(expectedTokens[index++], jt.next());
        }
    }
}
