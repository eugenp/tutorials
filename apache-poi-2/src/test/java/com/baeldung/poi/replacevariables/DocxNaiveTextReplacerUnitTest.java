package com.baeldung.poi.replacevariables;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

class DocxNaiveTextReplacerUnitTest {

    @Test
    void whenReplaceText_ThenTextReplaced() throws IOException {
        new DocxNaiveTextReplacer().replaceText();

        String filePath = getClass().getClassLoader()
          .getResource("baeldung-copy.docx")
          .getPath();
        try (FileInputStream fis = new FileInputStream(filePath); XWPFDocument document = new XWPFDocument(fis); XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            long occurrencesOfHello = Arrays.stream(extractor.getText()
                .split("\\s+"))
              .filter(s -> s.contains("Hello"))
              .count();
            assertTrue(occurrencesOfHello < 5);
        }
    }

}
