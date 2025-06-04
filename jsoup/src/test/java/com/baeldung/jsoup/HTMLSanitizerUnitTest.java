package com.baeldung.jsoup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class HTMLSanitizerUnitTest {

    @Test
    void givenScriptAndBasicTags_whenSanitizedWithBasicPolicy_thenStripScriptAndKeepFormatting() {
        String input = "<script>alert('XSS')</script><b>Hello</b> <a href='https://example.com'>link</a>";
        String expectedOutput = "<b>Hello</b> <a href=\"https://example.com\" rel=\"nofollow\">link</a>";

        String sanitized = HTMLSanitizer.sanitizeUsingBasic(input);
        assertEquals(expectedOutput, sanitized);
    }

    @Test
    void givenStyledHeadingAndUnsafeLink_whenSanitizedWithCustomPolicy_thenAllowOnlySafeContent() {
        String input = "<h1 class='title' style='color:red;'>Welcome</h1>"
            + "<a href='https://example.com' onclick='stealCookies()'>Click</a>"
            + "<script>alert('xss');</script>";
        String expectedOutput = "<h1 class=\"title\" style=\"color:red\">Welcome</h1><a href=\"https://example.com\" rel=\"nofollow\">Click</a>";
        String sanitized = HTMLSanitizer.sanitizeUsingCustomPolicy(input);
        assertEquals(expectedOutput, sanitized);
    }

    @Test
    void givenMixedHtml_whenSanitizedWithCustomPolicy_thenApplyCustomRules() {
        String input = "<div><span style='color:blue'>Hello</span><script>alert('hack')</script></div>";
        String expectedOutput = "<div><span style=\"color:blue\">Hello</span></div>";
        String sanitized = HTMLSanitizer.sanitizeUsingCustomPolicy(input);
        assertEquals(expectedOutput, sanitized);
    }

    @Test
    void givenJavascriptHrefAndTargetAttribute_whenSanitizedWithJsoup_thenOnlyAllowSafeContent() {
        String input = "<h1 onclick='x()'>Title</h1><a href='javascript:alert(1)' target='_blank'>Click</a>";
        String expectedOutput = "<h1>Title</h1><a target=\"_blank\" rel=\"nofollow\">Click</a>";
        String sanitized = HTMLSanitizer.sanitizeUsingJsoup(input);
        assertEquals(expectedOutput, sanitized);
    }
}
