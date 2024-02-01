package com.baeldung.urlnormalization;

import org.junit.jupiter.api.Test;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class URLNormalizationUnitTest {
    String originalUrl = "https://www.example.com:8080/path/to/resource?param1=value1&param2=value2#fragment";
    String expectedNormalizedUrl = "https://www.example.com:8080/path/to/resource";

    @Test
    public void givenOriginalUrl_whenUsingApacheCommonsValidator_thenValidatedAndMaybeManuallyNormalized() {
        UrlValidator urlValidator = new UrlValidator();
        if (urlValidator.isValid(originalUrl)) {
            String normalizedUri = originalUrl.split("\\?")[0];
            assertEquals(expectedNormalizedUrl, normalizedUri);
        } else {
            fail(originalUrl);
        }
    }

    @Test
    public void givenOriginalUrl_whenUsingJavaURIClass_thenNormalizedUrl() throws URISyntaxException {
        URI uri = new URI(originalUrl);
        URI normalizedUri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, null);
        String normalizedUrl = normalizedUri.toString();
        assertEquals(expectedNormalizedUrl, normalizedUrl);
    }

    @Test
    public void givenOriginalUrl_whenUsingRegularExpression_thenNormalizedUrl() {
        String regex = "^(https?://[^/]+/[^?#]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(originalUrl);

        if (matcher.find()) {
            String normalizedUrl = matcher.group(1);
            assertEquals(expectedNormalizedUrl, normalizedUrl);
        } else {
            fail(originalUrl);
        }
    }
}
