package com.baeldung.testdatafactory;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Converter {
    private static final Pattern TITLE_STRIP = Pattern.compile("#\\s*(.*)");

    /**
     * Convert the lines to paragraphs. Break sentences by full stops. Ignore formatting.
     * @param text input text
     * @return {@link Document} object
     */
    public static Document fromText(String text) {
        Document document = new Document();
        document.setParagraphs(Arrays.stream(text.split("\n")).map(Converter::toParagraph).collect(toList()));
        return document;
    }

    /**
     * Remove the # prefix from a title line
     * @param line the line
     * @return the non title part
     */
    private static String stripTitle(String line) {
        Matcher m = TITLE_STRIP.matcher(line);
        m.matches();
        return m.group(1);
    }


    /**
     * Convert a document into a plaintext
     * @param doc the document
     * @return plaintext form
     */
    public static String fromDocument(Document doc) {
        return doc.getParagraphs()
                .stream()
                .map(Converter::toParagraphPlaintext)
                .collect(Collectors.joining("\n"));
    }

    private static String toParagraphPlaintext(Paragraph paragraph) {
        return paragraph.getSentences().stream()
                .map(sentence -> String.join(" ", sentence.getTokens()))
                .collect(Collectors.joining(" "));
    }

    private static String toParagraphMarkdown(Paragraph paragraph) {
        return (paragraph.getStyle().equals(Paragraph.Style.HEADING) ? "# " : "") + toParagraphPlaintext(paragraph);
    }

    /**
     * Convert a document into markdown
     * @param doc the document
     * @return similar to plaintext, but with headings prefixed by #
     */
    public static String toMarkdown(Document doc) {
        return doc.getParagraphs()
                .stream()
                .map(Converter::toParagraphMarkdown)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Parse the markdown and return a document
     * @param markdown the markdown file
     * @return the document
     */
    public static Document fromMarkdown(String markdown) {
        Document document = new Document();
        document.setParagraphs(Arrays.stream(markdown.split("\n")).map(Converter::toParagraphFromMarkdown).collect(toList()));
        return document;
    }

    private static Paragraph toParagraphFromMarkdown(String line) {
        if (line.startsWith("#")) {
            return toParagraph(stripTitle(line), Paragraph.Style.HEADING);
        }
        return toParagraph(line, Paragraph.Style.NORMAL);
    }

    private static Paragraph toParagraph(String line) {
        return toParagraph(line, Paragraph.Style.NORMAL);
    }

    private static Paragraph toParagraph(String line, Paragraph.Style style) {
        Paragraph paragraph = new Paragraph();
        paragraph.setStyle(style);
        paragraph.setSentences(Arrays.stream(line.split("(?<=\\.)")).map(Converter::toSentence).collect(toList()));
        return paragraph;
    }

    private static Sentence toSentence(String sentenceText) {
        Sentence sentence = new Sentence();
        sentence.setTokens(Arrays.asList(sentenceText.split(" ")));
        return sentence;
    }

}
