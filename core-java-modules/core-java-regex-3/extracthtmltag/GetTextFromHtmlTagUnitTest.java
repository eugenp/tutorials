package com.baeldung.extracthtmltag;

import org.jsoup.Jsoup;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static org.junit.Assert.assertEquals;

public class GetTextFromHtmlTagUnitTest {

    @Test
    public void givenHtmlContentWithBoldTags_whenUsingPatternMatcherClasses_thenExtractText() {
        String htmlContent = "<div>This is a <b>Baeldung</b> article for <b>extracting text</b> from HTML tags.</div>";
        String tagName = "b";
        String patternString = "<" + tagName + ">(.*?)</" + tagName + ">";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(htmlContent);

        List<String> extractedTexts = new ArrayList<>();
        while (matcher.find()) {
            extractedTexts.add(matcher.group(1));
        }

        assertEquals("Baeldung", extractedTexts.get(0));
        assertEquals("extracting text", extractedTexts.get(1));
    }

    @Test
    public void givenHtmlContentWithParagraphTagsContainingNewLines_whenUsingPatternMatcherClasses_thenExtractText() {
        String htmlContent = "<div>This is a <p>multiline\nparagraph</p> with <p>line breaks</p>.</div>";
        String tagName = "p";
        String patternString = "(?s)<" + tagName + ">(.*?)</" + tagName + ">";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(htmlContent);

        List<String> extractedTexts = new ArrayList<>();
        while (matcher.find()) {
            extractedTexts.add(matcher.group(1));
        }

        assertEquals(2, extractedTexts.size());
        assertEquals("multiline\nparagraph", extractedTexts.get(0));
        assertEquals("line breaks", extractedTexts.get(1));
    }

    @Test
    public void givenHtmlContentWithNestedParagraphTags_whenUsingJsoup_thenExtractAllTextsFromHtmlTag() {
        String htmlContent = "<div>This is a <p>multiline\nparagraph <strong>with nested</strong> content</p> and <p>line breaks</p>.</div>";

        Document doc = Jsoup.parse(htmlContent);
        Elements paragraphElements = doc.select("p");

        List<String> extractedTexts = new ArrayList<>();
        for (Element paragraphElement : paragraphElements) {
            String extractedText = paragraphElement.text();
            extractedTexts.add(extractedText);
        }

        assertEquals(2, extractedTexts.size());
        assertEquals("multiline paragraph with nested content", extractedTexts.get(0));
        assertEquals("line breaks", extractedTexts.get(1));
    }
}
