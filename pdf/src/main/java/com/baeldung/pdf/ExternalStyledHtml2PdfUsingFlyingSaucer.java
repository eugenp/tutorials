package com.baeldung.pdf;

import com.lowagie.text.DocumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;

public class ExternalStyledHtml2PdfUsingFlyingSaucer {

    private static final String HTML_WITH_EXTERNAL_STYLE = "src/main/resources/openpdfhtmlwithexternalstyle.html";
    private static final String PDF_WITH_EXTERNAL_STYLE = "src/main/resources/ExternalStyledHtml2PdfUsingFlyingSaucer.pdf";

    public static void main(String[] args) {
        try {
            ExternalStyledHtml2PdfUsingFlyingSaucer htmlToPdf = new ExternalStyledHtml2PdfUsingFlyingSaucer();
            htmlToPdf.generatePDFFromHtmlWithExternalStyle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generatePDFFromHtmlWithExternalStyle() throws IOException {
        File inputHTML = new File(HTML_WITH_EXTERNAL_STYLE);
        String inputHtmlStr = createWellFormedHtml(inputHTML);
        File outputPdf = new File(PDF_WITH_EXTERNAL_STYLE);
        xhtmlToPdf(inputHtmlStr, outputPdf);
    }

    private String createWellFormedHtml(File inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    private void xhtmlToPdf(String xhtml, File outputPdf) throws IOException {
        try ( OutputStream outputStream = new FileOutputStream(outputPdf)){
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            sharedContext.setReplacedElementFactory(new CustomElementFactoryImpl());
            sharedContext.getTextRenderer().setSmoothingThreshold(0);
            renderer.getFontResolver()
                    .addFont("src/main/resources/fonts/Official.ttf", true);
            String baseUrl = FileSystems.getDefault()
                    .getPath("css").toUri().toURL().toString();
            renderer.setDocumentFromString(xhtml, baseUrl);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
