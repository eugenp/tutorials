package com.baeldung.addheaderfooterpdf;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.font.PdfFontFactory;

public class HeaderFooterEventHandler implements IEventHandler {
    @Override
    public void handleEvent(Event event) {
        if (event instanceof PdfDocumentEvent docEvent) {
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);

            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            canvas.beginText();
            try {
                canvas.setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 12);
            } catch (Exception e) {
                e.printStackTrace();
            }
            canvas.moveText(36, page.getPageSize().getTop() - 20);
            canvas.showText("Header text - Page " + pageNumber);
            canvas.endText();

            canvas.beginText();
            canvas.moveText(36, 20);
            canvas.showText("Footer text - Page " + pageNumber);
            canvas.endText();

            canvas.release();
        }
    }
}
