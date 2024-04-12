package com.baeldung.pdfinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

class PdfInfoITextUnitTest {

    private static final String PDF_FILE = "src/test/resources/input.pdf";

    @Test
    void givenPdf_whenGetNumberOfPages_thenOK() throws IOException {
        assertEquals(4, PdfInfoIText.getNumberOfPages(PDF_FILE));
    }

    @Test
    void givenPdf_whenIsPasswordRequired_thenOK() throws IOException {
        assertFalse(PdfInfoIText.isPasswordRequired(PDF_FILE));
    }

    @Test
    void givenPdf_whenGetInfo_thenOK() throws IOException {
        Map<String, String> info = PdfInfoIText.getInfo(PDF_FILE);
        assertEquals("LibreOffice 4.2", info.get("Producer"));
        assertEquals("Writer", info.get("Creator"));
    }
}
