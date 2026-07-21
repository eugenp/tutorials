package com.baeldung.poi.html;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.converter.WordToHtmlUtils;
import org.w3c.dom.Document;

public class DocToHtml {

public void convertDocToHtml(String docPath) throws Exception {
    Path input = Paths.get(docPath);
    String htmlFileName = input.getFileName().toString().replaceFirst("\\.[^.]+$", "") + ".html";
    Path output = input.resolveSibling(htmlFileName);
    Path imagesDir = input.resolveSibling("images");
    Files.createDirectories(imagesDir);
    try (InputStream in = Files.newInputStream(Paths.get(docPath)); OutputStream out = Files.newOutputStream(output)) {
        HWPFDocumentCore document = WordToHtmlUtils.loadDoc(in);
        Document htmlDocument = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .newDocument();
        WordToHtmlConverter converter = new WordToHtmlConverter(htmlDocument);
        converter.setPicturesManager((content, pictureType, suggestedName, widthInches, heightInches) -> {
            Path imageFile = imagesDir.resolve(suggestedName);
            try {
                Files.write(imageFile, content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "images/" + suggestedName;
        });
        converter.processDocument(document);
        Transformer transformer = TransformerFactory.newInstance()
            .newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "html");
        transformer.transform(new DOMSource(converter.getDocument()), new StreamResult(out));
    }
}

}
