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
		System.out.println("Creating HTML file from a PDF file: " + filename);
		PDDocument pdf = null;
		try {
			// load the PDF file using PDFBox
			pdf = PDDocument.load(new File(filename));
			// create the DOM parser
			PDFDomTree parser = new PDFDomTree();
			// parse the file and get the DOM Document
			Writer output = new PrintWriter("src/output/pdf.html", "utf-8");
			parser.writeText(pdf, output);
			output.close();
		} finally {
			if (pdf != null) {
				try {
					pdf.close();
				} catch (IOException e) {
					System.err.println("Error: " + e.getMessage());
				}
			}
		}
		System.out.println("Done.");
	}

}
