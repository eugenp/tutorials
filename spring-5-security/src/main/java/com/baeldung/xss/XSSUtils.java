package com.baeldung.xss;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.owasp.esapi.ESAPI;

public class XSSUtils {
    private XSSUtils() {
    }

    public static String stripXSS(String value) {
        if (value != null) {
            value = ESAPI.encoder()
                .canonicalize(value);
            value = value.replaceAll("\0", "");
            value = Jsoup.clean(value, Whitelist.none());
        }
        return value;
    }

}
