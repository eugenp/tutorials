package com.baeldung.pdf;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;

public class PDF2HTMLExample {

	private static final String FILENAME = "src/main/resources/pdf.pdf";

	public static void main(String[] args) {
		try {
			generateHTMLFromPDF(FILENAME);
		} catch (IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private static void generateHTMLFromPDF(String filename) throws ParserConfigurationException, IOException {
		PDDocument pdf = PDDocument.load(new File(filename));
		PDFDomTree parser = new PDFDomTree();
		Writer output = new PrintWriter("src/output/pdf.html", "utf-8");
		parser.writeText(pdf, output);
		output.close();
		if (pdf != null) {
			pdf.close();
		}
	}
}
