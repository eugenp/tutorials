package com.baeldung.pdf;

import com.lowagie.text.DocumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;

public class HtmlWithExternalStyle2PdfUsingOpenPdf {

    private static final String HTML_WITH_EXTERNAL_STYLE = "src/main/resources/openpdfhtmlwithinlinestyle.html";
    private static final String PDF_WITH_EXTERNAL_STYLE = "src/main/resources/openpdfhtmlwithinlinestyle.pdf";

    public static void main(String[] args) {
        try {
            generatePDFFromHtmlWithExternalStyle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generatePDFFromHtmlWithExternalStyle() throws Exception {
        File inputHTML = new File(HTML_WITH_EXTERNAL_STYLE);
        String inputHtmlStr = createWellFormedHtml(inputHTML);
        File outputPdf = new File(PDF_WITH_EXTERNAL_STYLE);
        xhtmlToPdf(inputHtmlStr, outputPdf);
    }

    private static String createWellFormedHtml(File inputHTML) throws Exception {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    private static void xhtmlToPdf(String xhtml, File outputPdf) throws Exception {
        OutputStream outputStream = null;
        try {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            sharedContext.getTextRenderer().setSmoothingThreshold(0);
            renderer.getFontResolver()
                    .addFont(HtmlWithExternalStyle2PdfUsingOpenPdf.class.getClassLoader()
                            .getResource("src/resources/styles/fonts/PRISTINA.ttf").toString(), true);
            String baseUrl = FileSystems.getDefault()
                    .getPath("src//resources//styles//").toUri().toURL().toString();
            renderer.setDocumentFromString(xhtml);
            renderer.layout();
            outputStream = new FileOutputStream(outputPdf);
            renderer.createPDF(outputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null)
                outputStream.close();
        }
    }
}
