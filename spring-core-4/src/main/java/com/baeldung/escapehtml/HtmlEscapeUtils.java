package com.baeldung.escapehtml;

import com.google.common.html.HtmlEscapers;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

public class HtmlEscapeUtils {

    public static String escapeWithApacheCommons(String input) {
        return StringEscapeUtils.escapeHtml4(input);
    }

    public static String escapeWithGuava(String input) {
        return HtmlEscapers.htmlEscaper().escape(input);
    }

    public static String escapeWithSpring(String input) {
        return HtmlUtils.htmlEscape(input);
    }

}
