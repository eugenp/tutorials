package com.baeldung.testdatafactory;

import static java.util.Arrays.asList;

public class TestDataFactory {

    public static String twoParagraphs() {
        return "Paragraph one starts here.\n" +
                "Then paragraph two follows. It has two sentences.";
    }

    public static Document twoParagraphsAsDocument() {
        Paragraph paragraph1 = new Paragraph();
        paragraph1.setStyle(Paragraph.Style.NORMAL);

        Sentence sentence1 = new Sentence();
        sentence1.setTokens(asList("Paragraph", "one", "starts", "here."));
        paragraph1.setSentences(asList(sentence1));

        Paragraph paragraph2 = new Paragraph();
        paragraph2.setStyle(Paragraph.Style.NORMAL);
        Sentence sentence2 = new Sentence();
        sentence2.setTokens(asList("Then", "paragraph", "two", "follows."));
        Sentence sentence3 = new Sentence();
        sentence3.setTokens(asList("It", "has", "two", "sentences."));
        paragraph2.setSentences(asList(sentence2, sentence3));

        Document document = new Document();
        document.setParagraphs(asList(paragraph1, paragraph2));
        return document;
    }
}
