package com.baeldung.iTextPDFTest;

import static com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor.getTextFromPage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.iTextPDF.StoryTime;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

class AddWaterMarkToExistingPdfUnitTest {

    @Test
    public void givenAnExistingPDF_whenManipulatedPDFWithIText_thenGeneratePDFwithWatermarks() throws IOException {
        StoryTime storyTime = new StoryTime();
        String outputPdf = "output/aliceupdated.pdf";
        String watermark = "CONFIDENTIAL";

        LocationTextExtractionStrategy extStrategy = new LocationTextExtractionStrategy();
        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(outputPdf))) {
            for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
                String textFromPage = getTextFromPage(pdfDocument.getPage(i), extStrategy);
                assertThat(textFromPage).contains(watermark);
            }
        }

    }

}
