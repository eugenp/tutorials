package com.baeldung.pdf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Html2PdfUsingFlyingSaucer {

	private static final String HTML_INPUT = "src/main/resources/htmlforopenpdf.html";
	private static final String PDF_OUTPUT = "src/main/resources/html2pdf.pdf";

	public static void main(String[] args) {
		try {
			Html2PdfUsingFlyingSaucer htmlToPdf = new Html2PdfUsingFlyingSaucer();
			htmlToPdf.generateHtmlToPdf();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateHtmlToPdf() throws IOException {
		File inputHTML = new File(HTML_INPUT);
		String inputHtmlStr = createWellFormedHtml(inputHTML);
		File outputPdf = new File(PDF_OUTPUT);
		xhtmlToPdf(inputHtmlStr, outputPdf);
	}

	private String createWellFormedHtml(File inputHTML) throws IOException {
		Document document = Jsoup.parse(inputHTML, "UTF-8");
		document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
		return document.html();
	}

	private void xhtmlToPdf(String xhtml, File outputPdf) throws IOException {
		try (OutputStream outputStream = new FileOutputStream(outputPdf)) {
			ITextRenderer renderer = new ITextRenderer();
			SharedContext sharedContext = renderer.getSharedContext();
			sharedContext.setPrint(true);
			sharedContext.setInteractive(false);
			renderer.setDocumentFromString(xhtml);
			renderer.layout();
			renderer.createPDF(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
