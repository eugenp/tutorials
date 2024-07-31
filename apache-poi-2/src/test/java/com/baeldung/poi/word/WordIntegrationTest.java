package com.baeldung.poi.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WordIntegrationTest {
    static WordDocument wordDocument;

    @BeforeClass
    public static void generateMSWordFile() throws Exception {
        WordIntegrationTest.wordDocument = new WordDocument();
        wordDocument.handleSimpleDoc();
    }

    @Test
    public void whenParsingOutputDocument_thenCorrect() throws Exception {
        Path msWordPath = Paths.get(WordDocument.output);
        XWPFDocument document = new XWPFDocument(Files.newInputStream(msWordPath));
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        document.close();

        XWPFParagraph title = paragraphs.get(0);
        XWPFRun titleRun = title.getRuns().get(0);
        assertEquals("Build Your REST API with Spring", title.getText());
        assertEquals("009933", titleRun.getColor());
        assertTrue(titleRun.isBold());
        assertEquals("Courier", titleRun.getFontFamily());
        assertEquals(20, titleRun.getFontSize());

        assertEquals("from HTTP fundamentals to API Mastery", paragraphs.get(1).getText());
        assertEquals("What makes a good API?", paragraphs.get(3).getText());
        assertEquals(wordDocument.convertTextFileToString(WordDocument.paragraph1), paragraphs.get(4).getText());
        assertEquals(wordDocument.convertTextFileToString(WordDocument.paragraph2), paragraphs.get(5).getText());
        assertEquals(wordDocument.convertTextFileToString(WordDocument.paragraph3), paragraphs.get(6).getText());
    }
}
