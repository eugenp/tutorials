package com.baeldung.extractip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class ExtractIpAddressUnitTest {

    private static final String INPUT1 = "No IP address here";
    private static final List<String> EXPECTED1 = Collections.emptyList();

    private static final String INPUT2 = "My local ip is 127.0.0.1";
    private static final List<String> EXPECTED2 = List.of("127.0.0.1");

    private static final String INPUT3 = "Another ip address is 192.168.42.42";
    private static final List<String> EXPECTED3 = List.of("192.168.42.42");

    private static final String INPUT4 = "Extract the valid part: 260.1.2.345";
    private static final List<String> EXPECTED4 = List.of("60.1.2.34");

    private static final String INPUT5 = "No valid ip address 260.42.342.345";
    private static final List<String> EXPECTED5 = Collections.emptyList();

    private static final String INPUT6 = "We have multiple ip addresses: 127.1.1.0, 192.168.42.42 and 245.30.1.34";
    private static final List<String> EXPECTED6 = List.of("127.1.1.0", "192.168.42.42", "245.30.1.34");

    private static final Pattern IP_PATTERN = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)[.]){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");

    List<String> extractIP(String input) {
        Matcher matcher = IP_PATTERN.matcher(input);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    @Test
    void whenExtractIpAddress_thenCorrect() {
        assertEquals(EXPECTED1, extractIP(INPUT1));
        assertEquals(EXPECTED2, extractIP(INPUT2));
        assertEquals(EXPECTED3, extractIP(INPUT3));
        assertEquals(EXPECTED4, extractIP(INPUT4));
        assertEquals(EXPECTED5, extractIP(INPUT5));
        assertEquals(EXPECTED6, extractIP(INPUT6));
    }
}
