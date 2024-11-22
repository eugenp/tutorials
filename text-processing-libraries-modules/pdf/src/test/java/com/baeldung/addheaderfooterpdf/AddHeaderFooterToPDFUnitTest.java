package com.baeldung.addheaderfooterpdf;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddHeaderFooterToPDFUnitTest {

    @Test
    void givenHeaderAndFooter_whenCreatingPDF_thenHeaderFooterAreOnEachPage() throws IOException {
        String dest = "documentWithHeaderFooter.pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        HeaderFooterEventHandler handler = new HeaderFooterEventHandler();
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, handler);

        document.add(new Paragraph("This document contains a header and footer on every page."));
        document.close();
    }

    @Test
    void givenHeaderAndFooter_whenTestingPDF_thenHeaderFooterAreVerified() throws IOException {
        String dest = "documentWithHeaderFooter.pdf";

        PDDocument pdDocument = PDDocument.load(new File(dest));
        PDFTextStripper stripper = new PDFTextStripper();

        String text = stripper.getText(pdDocument);
        pdDocument.close();

        assertTrue(text.contains("Header text"));
        assertTrue(text.contains("Footer text"));
    }
}
