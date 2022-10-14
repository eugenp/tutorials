package com.baeldung.pdfedition;

import java.io.IOException;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IPdfTextLocation;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.pdfcleanup.PdfCleaner;
import com.itextpdf.pdfcleanup.autosweep.CompositeCleanupStrategy;
import com.itextpdf.pdfcleanup.autosweep.RegexBasedCleanupStrategy;

public class PdfTextReplacement {

    private static final String SOURCE = "src/main/resources/baeldung-modified.pdf";
    private static final String DESTINATION = "src/main/resources/baeldung-fixed.pdf";

    public static void main(String[] args) throws IOException {
        PdfReader reader = new PdfReader(SOURCE);
        PdfWriter writer = new PdfWriter(DESTINATION);
        PdfDocument pdfDocument = new PdfDocument(reader, writer);
        replaceTextContentFromDocument(pdfDocument);
        pdfDocument.close();
    }

    private static void replaceTextContentFromDocument(PdfDocument pdfDocument) throws IOException {
        CompositeCleanupStrategy strategy = new CompositeCleanupStrategy();
        strategy.add(new RegexBasedCleanupStrategy("Baeldung tutorials").setRedactionColor(ColorConstants.WHITE));
        PdfCleaner.autoSweepCleanUp(pdfDocument, strategy);

        for (IPdfTextLocation location : strategy.getResultantLocations()) {
            PdfPage page = pdfDocument.getPage(location.getPageNumber() + 1);
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamAfter(), page.getResources(), page.getDocument());
            Canvas canvas = new Canvas(pdfCanvas, location.getRectangle());
            canvas.add(new Paragraph("HIDDEN").setFontSize(8)
                .setMarginTop(0f));
        }
    }

}
