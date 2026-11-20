package com.baeldung.poi.html;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class DocToHtmlUnitTest {
    @Test
    void givenSimpleDoc_whenConverting_thenHtmlFileIsCreated() throws Exception {
        DocToHtml converter = new DocToHtml();
        Path doc = Paths.get(this.getClass()
            .getResource("/sample.doc")
            .getPath());

        converter.convertDocToHtml(doc.toString());

        Path html = doc.resolveSibling("sample.html");
        assertTrue(Files.exists(html));

        String content = Files.lines(html, StandardCharsets.UTF_8)
            .collect(Collectors.joining("\n"));
        assertTrue(content.contains("<html"));
    }
}