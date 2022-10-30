package com.baeldung.pdfedition;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.pdfcleanup.CleanUpProperties;
import com.itextpdf.pdfcleanup.PdfCleanUpLocation;
import com.itextpdf.pdfcleanup.PdfCleanUpTool;
import com.itextpdf.pdfcleanup.PdfCleaner;
import com.itextpdf.pdfcleanup.autosweep.CompositeCleanupStrategy;
import com.itextpdf.pdfcleanup.autosweep.RegexBasedCleanupStrategy;

public class PdfContentRemover {

    private static final String SOURCE = "src/main/resources/baeldung-modified.pdf";
    private static final String DESTINATION = "src/main/resources/baeldung-cleaned.pdf";

    public static void main(String[] args) throws IOException {
        PdfReader reader = new PdfReader(SOURCE);
        PdfWriter writer = new PdfWriter(DESTINATION);
        PdfDocument pdfDocument = new PdfDocument(reader, writer);
        removeContentFromDocument(pdfDocument);
        pdfDocument.close();
    }

    private static void removeContentFromDocument(PdfDocument pdfDocument) throws IOException {
        // 5.1. remove text
        CompositeCleanupStrategy strategy = new CompositeCleanupStrategy();
        strategy.add(new RegexBasedCleanupStrategy("Baeldung"));
        PdfCleaner.autoSweepCleanUp(pdfDocument, strategy);

        // 5.2. remove other areas
        List<PdfCleanUpLocation> cleanUpLocations = Arrays.asList(new PdfCleanUpLocation(1, new Rectangle(10, 50, 90, 70)), new PdfCleanUpLocation(2, new Rectangle(35, 400, 100, 35)));
        PdfCleanUpTool cleaner = new PdfCleanUpTool(pdfDocument, cleanUpLocations, new CleanUpProperties());
        cleaner.cleanUp();
    }

}
