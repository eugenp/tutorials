package com.baeldung.poi.replacevariables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

class DocxTestReplacerUnitTest {

    @Test
    void whenReplaceText_ThenTextReplaced() throws IOException {
        new DocxTextReplacer().replaceText();

        String filePath = getClass().getClassLoader()
          .getResource("baeldung.docx")
          .getPath();
        try (FileInputStream fis = new FileInputStream(filePath); XWPFDocument document = new XWPFDocument(fis); XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            long occurrencesOfHello = Arrays.stream(extractor.getText()
                .split("\\s+"))
              .filter(s -> s.contains("Hello"))
              .count();
            assertEquals(5, occurrencesOfHello);
        }
    }

}
