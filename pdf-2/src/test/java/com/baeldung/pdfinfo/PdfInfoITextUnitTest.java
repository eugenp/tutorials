package com.baeldung.pdfinfo;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class PdfInfoITextUnitTest {

    private static final String PDF_FILE = "src/test/resources/input.pdf";

    @Test
    public void givenPdf_whenGetNumberOfPages_thenOK() throws IOException {
        Assert.assertEquals(4, PdfInfoIText.getNumberOfPages(PDF_FILE));
    }

    @Test
    public void givenPdf_whenIsPasswordRequired_thenOK() throws IOException {
        Assert.assertFalse(PdfInfoIText.isPasswordRequired(PDF_FILE));
    }

    @Test
    public void givenPdf_whenGetInfo_thenOK() throws IOException {
        Map<String, String> info = PdfInfoIText.getInfo(PDF_FILE);
        Assert.assertEquals("LibreOffice 4.2", info.get("Producer"));
        Assert.assertEquals("Writer", info.get("Creator"));
    }
}
