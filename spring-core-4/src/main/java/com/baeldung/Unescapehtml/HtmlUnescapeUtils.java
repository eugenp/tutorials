package com.baeldung.Unescapehtml;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;
import org.unbescape.html.HtmlEscape;

public class HtmlUnescapeUtils {
    public static String unescapeWithApacheCommons(String htmlInput) {
        return StringEscapeUtils.unescapeHtml4(htmlInput);
    }

    public static String unescapeWithSpring(String htmlInput) {
        return HtmlUtils.htmlUnescape(htmlInput);
    }

    public static String unescapeWithUnbescape(String htmlInput) {
        return HtmlEscape.unescapeHtml(htmlInput);
    }
}
