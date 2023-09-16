package com.baeldung.stringsplitkeyvalue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringSplitKeyValueUnitTest {
    @Test
    public void given_StringData_whenUsingTokenizer_thenTokenizeAndValidate() {
        String data = "name=John age=30 city=NewYork";
        StringTokenizer tokenizer = new StringTokenizer(data);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            String[] keyValue = token.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];

                // Use assertions to validate the key-value pairs
                if (key.equals("name")) {
                    assertEquals("John", value);
                } else if (key.equals("age")) {
                    assertEquals("30", value);
                } else if (key.equals("city")) {
                    assertEquals("NewYork", value);
                } else {
                    // Handle unexpected keys or values if needed
                    // You can throw an exception or log a message here.
                }
            }
        }
    }

    @Test
    public void given_DataWithPattern_whenUsingMatcher_thenPerformPatternMatching() {
        String data = "name=John,age=30;city=NewYork";
        Pattern pattern = Pattern.compile("\\b(\\w+)=(\\w+)\\b");
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);

            // Use assertions to validate the key-value pairs
            if (key.equals("name")) {
                assertEquals("John", value);
            } else if (key.equals("age")) {
                assertEquals("30", value);
            } else if (key.equals("city")) {
                assertEquals("NewYork", value);
            } else {
                // Handle unexpected keys or values if needed
                // You can throw an exception or log a message here.
            }
        }
    }

    @Test
    public void given_StringData_whenUsingJavaMap_thenSplitAndValidate() {
        String data = "name=John age=30 city=NewYork";
        Map<String, String> keyValueMap = Arrays.stream(data.split(" "))
                .map(kv -> kv.split("="))
                .filter(kvArray -> kvArray.length == 2)
                .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));

        assertEquals("John", keyValueMap.get("name"));
        assertEquals("30", keyValueMap.get("age"));
        assertEquals("NewYork", keyValueMap.get("city"));
    }
}
