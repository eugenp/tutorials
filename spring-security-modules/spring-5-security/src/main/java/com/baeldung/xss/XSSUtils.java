package com.baeldung.xss;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.owasp.esapi.ESAPI;

public class XSSUtils {

    public static String stripXSS(String value) {
        if (value == null) {
            return null;
        }
        value = ESAPI.encoder()
            .canonicalize(value)
              .replaceAll("\0", "");
        return Jsoup.clean(value, Whitelist.none());
    }

}
