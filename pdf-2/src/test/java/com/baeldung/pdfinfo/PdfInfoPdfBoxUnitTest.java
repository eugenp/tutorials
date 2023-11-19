package com.baeldung.pdfinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class PdfInfoPdfBoxUnitTest {

    private static final String PDF_FILE = "src/test/resources/input.pdf";

    @Test
    void givenPdf_whenGetNumberOfPages_thenOK() throws IOException {
        assertEquals(4, PdfInfoPdfBox.getNumberOfPages(PDF_FILE));
    }

    @Test
    void givenPdf_whenIsPasswordRequired_thenOK() throws IOException {
        assertFalse(PdfInfoPdfBox.isPasswordRequired(PDF_FILE));
    }

    @Test
    void givenPdf_whenGetInfo_thenOK() throws IOException {
        PDDocumentInformation info = PdfInfoPdfBox.getInfo(PDF_FILE);
        assertEquals("LibreOffice 4.2", info.getProducer());
        assertEquals("Writer", info.getCreator());
    }
}
