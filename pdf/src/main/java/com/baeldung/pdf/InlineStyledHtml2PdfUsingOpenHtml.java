package com.baeldung.pdf;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;

public class InlineStyledHtml2PdfUsingOpenHtml {

    private static final String HTML_STYLE = "src/main/resources/openpdfhtmlwithexternalstyle.html";
    private static final String PDF_STYLE = "src/main/resources/InlineStyledHtml2PdfUsingFlyingSaucer.pdf";

    public static void main(String[] args) {
        try {
            InlineStyledHtml2PdfUsingOpenHtml htmlToPdf = new InlineStyledHtml2PdfUsingOpenHtml();
            htmlToPdf.generatePDFFromHtmlWithInlineStyle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generatePDFFromHtmlWithInlineStyle() throws Exception {
        File inputHTML = new File(HTML_STYLE);
        org.w3c.dom.Document doc = createWellFormedHtml(inputHTML);
        xhtmlToPdf(doc, PDF_STYLE);
    }

    private org.w3c.dom.Document createWellFormedHtml(File inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return new W3CDom().fromJsoup(document);
    }

    private void xhtmlToPdf(org.w3c.dom.Document doc, String outputPdf) throws IOException {
        String baseUri = FileSystems.getDefault()
                .getPath( "src/main/resources/css")
                .toUri()
                .toString();
        OutputStream os = new FileOutputStream(outputPdf);
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withUri(outputPdf);
        builder.toStream(os);
        builder.useFont(new File(getClass().getClassLoader().getResource("fonts/Official.ttf").getFile()), "Official");
        builder.withW3cDocument(doc, baseUri);
        builder.run();
        os.close();
    }
}
