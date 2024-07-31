package com.baeldung.poi.replacevariables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.junit.jupiter.api.Test;

class DocTextReplacerUnitTest {

    @Test
    void whenReplaceText_ThenTextReplaced() throws IOException {
        new DocTextReplacer().replaceText();

        String filePath = getClass().getClassLoader()
          .getResource("baeldung.doc")
          .getPath();
        try (FileInputStream fis = new FileInputStream(filePath); HWPFDocument document = new HWPFDocument(fis); WordExtractor extractor = new WordExtractor(document)) {
            long occurrencesOfHello = Arrays.stream(extractor.getText()
                .split("\\s+"))
              .filter(s -> s.contains("Hello"))
              .count();
            assertEquals(5, occurrencesOfHello);
        }
    }

}
