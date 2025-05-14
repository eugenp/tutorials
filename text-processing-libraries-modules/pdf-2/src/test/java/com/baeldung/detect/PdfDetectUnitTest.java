package com.baeldung.detect;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.util.Objects;

import com.itextpdf.commons.exceptions.ITextException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;


public class PdfDetectUnitTest {

    private static final File PDF_FILE = new File("src/test/resources/input.pdf");

    @Test
    void whenDetectPdfByPdfBox_thenCorrect() {
        boolean isPdf;
        try (PDDocument document = Loader.loadPDF(PDF_FILE)) {
            isPdf = true;
        } catch (IOException ioe) {
            isPdf = false;
        }
        assertTrue(isPdf);
    }

    @Test
    void whenDetectPdfByItext_thenCorrect() {
        boolean isPdf;
        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(PDF_FILE))) {
            isPdf = true;
        } catch (ITextException | IOException e) {
            isPdf = false;
        }
        assertTrue(isPdf);
    }

    @Test
    void whenDetectPdfByFileSignature_thenCorrect() throws IOException {
        boolean isPdf = false;
        try (InputStream fis = new BufferedInputStream(new FileInputStream(PDF_FILE))) {
            byte[] bytes = new byte[5];
            if (fis.read(bytes) == 5) {
                String header = new String(bytes);
                isPdf = Objects.equals(header, "%PDF-");
            }
        }
        assertTrue(isPdf);
    }

    @Test
    void whenDetectPdfByTika_thenCorrect() throws IOException {
        Tika tika = new Tika();
        boolean isPdf = Objects.equals(tika.detect(PDF_FILE), "application/pdf");
        assertTrue(isPdf);
    }

}