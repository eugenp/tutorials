package com.baeldung.poi.html;

import fr.opensagres.poi.xwpf.converter.core.ImageManager;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DocxToHtml {

    public XWPFDocument loadDocxFromPath(String path) {
        try {
            Path file = Paths.get(path);
            if (!Files.exists(file)) {
                throw new FileNotFoundException("File not found: " + path);
            }
            XWPFDocument document = new XWPFDocument(Files.newInputStream(file));
            boolean hasParagraphs = !document.getParagraphs().isEmpty();
            boolean hasTables = !document.getTables().isEmpty();
            if (!hasParagraphs && !hasTables) {
                document.close();
                throw new IllegalArgumentException("Document is empty: " + path);
            }
            return document;
        } catch (IOException ex) {
            throw new UncheckedIOException("Cannot load document: " + path, ex);
        }
    }

    private XHTMLOptions configureHtmlOptions(Path outputDir) {
        XHTMLOptions options = XHTMLOptions.create();
        options.setImageManager(new ImageManager(outputDir.toFile(), "images"));
        return options;
    }

    public void convertDocxToHtml(String docxPath) throws IOException {
        Path input = Paths.get(docxPath);
        String htmlFileName = input.getFileName().toString().replaceFirst("\\.[^.]+$", "") + ".html";
        Path output = input.resolveSibling(htmlFileName);
        try (XWPFDocument document = loadDocxFromPath(docxPath); OutputStream out = Files.newOutputStream(output)) {
            XHTMLConverter.getInstance().convert(document, out, configureHtmlOptions(output.getParent()));
        }
    }

}
