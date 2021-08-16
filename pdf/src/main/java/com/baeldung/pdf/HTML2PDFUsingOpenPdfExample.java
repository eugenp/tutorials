package com.baeldung.pdf;

import com.itextpdf.text.DocumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HTML2PDFUsingOpenPdfExample {

    private static final String HTML = "src/main/resources/openpdfhtmlexample.html";
    private static final String PDF = "src/main/resources/openpdfhtmlexample.pdf";

    public static void main(String[] args) {
        try {
            File inputHTML = new File(HTML);
            generatePDFFromHTMLUsingOpenPdfFlyingSaucer(inputHTML);
        } catch (IOException | ParserConfigurationException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private static void generatePDFFromHTMLUsingOpenPdfFlyingSaucer(File inputHTML) throws ParserConfigurationException, IOException, DocumentException {
        String inputHtmlStr = createWellFormedHtml(inputHTML);
        File outputPdf = new File(PDF);
        xhtmlToPdf(inputHtmlStr, outputPdf);
    }

    private static String createWellFormedHtml(File inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    private static void xhtmlToPdf(String xhtml, File outputPdf) throws IOException {
        OutputStream outputStream = null;
        try {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            sharedContext.getTextRenderer().setSmoothingThreshold(0);
            renderer.setDocumentFromString(xhtml);
            renderer.layout();
            outputStream = new FileOutputStream(outputPdf);
            renderer.createPDF(outputStream);
        } catch (com.lowagie.text.DocumentException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null)
                outputStream.close();
        }
    }
}
