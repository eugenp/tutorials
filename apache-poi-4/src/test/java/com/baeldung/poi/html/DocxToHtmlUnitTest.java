package com.baeldung.poi.html;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class DocxToHtmlUnitTest {

    @Test
    void givenSimpleDocx_whenConverting_thenHtmlFileIsCreated() throws IOException {
        DocxToHtml converter = new DocxToHtml();
        Path docx = Paths.get(this.getClass()
            .getResource("/sample.docx")
            .getPath());
        converter.convertDocxToHtml(docx.toString());
        Path html = docx.resolveSibling("sample.html");
        assertTrue(Files.exists(html));
        String content = Files.lines(html, StandardCharsets.UTF_8)
            .collect(Collectors.joining("\n"));
        assertTrue(content.contains("<html"));
    }

}