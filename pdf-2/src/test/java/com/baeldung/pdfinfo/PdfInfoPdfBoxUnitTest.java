package com.baeldung.pdfinfo;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PdfInfoPdfBoxUnitTest {

    private static final String PDF_FILE = "src/test/resources/input.pdf";

    @Test
    public void givenPdf_whenGetNumberOfPages_thenOK() throws IOException {
        Assert.assertEquals(4, PdfInfoPdfBox.getNumberOfPages(PDF_FILE));
    }

    @Test
    public void givenPdf_whenIsPasswordRequired_thenOK() throws IOException {
        Assert.assertFalse(PdfInfoPdfBox.isPasswordRequired(PDF_FILE));
    }

    @Test
    public void givenPdf_whenGetInfo_thenOK() throws IOException {
        PDDocumentInformation info = PdfInfoPdfBox.getInfo(PDF_FILE);
        Assert.assertEquals("LibreOffice 4.2", info.getProducer());
        Assert.assertEquals("Writer", info.getCreator());
    }
}
