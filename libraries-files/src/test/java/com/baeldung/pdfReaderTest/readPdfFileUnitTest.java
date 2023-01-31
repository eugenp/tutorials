package com.baeldung.pdfReaderTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

class readPdfFileUnitTest {

    @Test
    void givenSamplePdf_whenUsingApachePdfBox_thenCompareOutput() throws IOException {
        String expectedText = "Hello World!\n";

        File file = new File("output/sample.pdf");
        PDDocument document = PDDocument.load(file);

        PDFTextStripper stripper = new PDFTextStripper();

        String text = stripper.getText(document);

        document.close();

        assertEquals(expectedText, text);

    }

    @Test
    void givenSamplePdf_whenUsingiTextPdf_thenCompareOutput() throws IOException {
        String expectedText = "Hello World!";

        PdfReader reader = new PdfReader("output/sample.pdf");
        int pages = reader.getNumberOfPages();
        StringBuilder text = new StringBuilder();

        for (int i = 1; i <= pages; i++) {

            text.append(PdfTextExtractor.getTextFromPage(reader, i));

        }
        reader.close();
        assertEquals(expectedText, text.toString());

    }

}
