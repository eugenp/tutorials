package com.baeldung.pdf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class InlineStyledHtml2PdfUsingFlyingSaucer {

    private static final String HTML_WITH_INLINE_STYLE = "src/main/resources/openpdfhtmlwithinlinestyle.html";
    private static final String PDF_WITH_INTERNAL_STYLE = "src/main/resources/InlineStyledHtml2PdfUsingFlyingSaucer.pdf";

    public static void main(String[] args) {
        try {
            InlineStyledHtml2PdfUsingFlyingSaucer htmlToPdf = new InlineStyledHtml2PdfUsingFlyingSaucer();
            htmlToPdf.generatePDFFromHtmlWithInlineStyle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generatePDFFromHtmlWithInlineStyle() throws Exception {
        File inputHTML = new File(HTML_WITH_INLINE_STYLE);
        String inputHtmlStr = createWellFormedHtml(inputHTML);
        File outputPdf = new File(PDF_WITH_INTERNAL_STYLE);
        xhtmlToPdf(inputHtmlStr, outputPdf);
    }

    private String createWellFormedHtml(File inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    private void xhtmlToPdf(String xhtml, File outputPdf) throws IOException {
        OutputStream outputStream = null;
        try {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            renderer.setDocumentFromString(xhtml);
            renderer.layout();
            outputStream = new FileOutputStream(outputPdf);
            renderer.createPDF(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null)
                outputStream.close();
        }
    }
}
