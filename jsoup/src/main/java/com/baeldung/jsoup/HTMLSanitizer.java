package com.baeldung.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class HTMLSanitizer {

    private static final PolicyFactory POLICY = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
    private static final PolicyFactory HTML_POLICY = new HtmlPolicyBuilder().allowCommonBlockElements()
        .allowCommonInlineFormattingElements()
        .toFactory();

    private static final PolicyFactory CUSTOM_POLICY = new HtmlPolicyBuilder().allowElements("a", "p", "div", "span", "h1", "h2", "h3")
        .allowUrlProtocols("https")
        .allowAttributes("href")
        .onElements("a")
        .requireRelNofollowOnLinks()
        .allowAttributes("class")
        .globally()
        .allowStyling()
        .toFactory();

    public static String sanitizeUsingBasic(String htmlContent) {
        return POLICY.sanitize(htmlContent);
    }

    public static String sanitizeUsingHTMLPolicy(String html) {
        return HTML_POLICY.sanitize(html);
    }

    public static String sanitizeUsingCustomPolicy(String html) {
        return CUSTOM_POLICY.sanitize(html);
    }

    public static String sanitizeUsingJsoup(String html) {
        Safelist safelist = Safelist.basic()
            .addTags("h1", "h2", "h3")
            .addAttributes("a", "target")
            .addProtocols("a", "href", "http", "https");
        return Jsoup.clean(html, safelist);
    }
}
