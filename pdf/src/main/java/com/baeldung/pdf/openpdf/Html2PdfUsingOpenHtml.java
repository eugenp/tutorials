package com.baeldung.pdf.openpdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class Html2PdfUsingOpenHtml {

	private static final String HTML_INPUT = "src/main/resources/htmlforopenpdf.html";
	private static final String PDF_OUTPUT = "src/main/resources/html2pdf.pdf";

	public static void main(String[] args) {
		try {
			Html2PdfUsingOpenHtml htmlToPdf = new Html2PdfUsingOpenHtml();
			htmlToPdf.generateHtmlToPdf();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateHtmlToPdf() throws IOException {
		File inputHTML = new File(HTML_INPUT);
		org.w3c.dom.Document doc = createWellFormedHtml(inputHTML);
		xhtmlToPdf(doc, PDF_OUTPUT);
	}

	private org.w3c.dom.Document createWellFormedHtml(File inputHTML) throws IOException {
		Document document = Jsoup.parse(inputHTML, "UTF-8");
		document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
		return new W3CDom().fromJsoup(document);
	}

	private void xhtmlToPdf(org.w3c.dom.Document doc, String outputPdf) throws IOException {
		OutputStream os = new FileOutputStream(outputPdf);
		PdfRendererBuilder builder = new PdfRendererBuilder();
		builder.withUri(outputPdf);
		builder.toStream(os);
		builder.withW3cDocument(doc, "");
		builder.run();
		os.close();
	}
}
