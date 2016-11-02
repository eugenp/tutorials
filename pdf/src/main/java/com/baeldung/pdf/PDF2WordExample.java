package com.baeldung.pdf;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class PDF2WordExample {

	private static final String FILENAME = "src/main/resources/pdf.pdf";

	public static void main(String[] args) {
		try {
			generateDocFromPDF(FILENAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generateDocFromPDF(String filename) throws IOException {
		System.out.println("Creating a docx file from a PDF file: " + filename);
		// Create the word document
		XWPFDocument doc = new XWPFDocument();

		// Open the pdf file
		String pdf = filename;
		PdfReader reader = new PdfReader(pdf);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);

		// Read the PDF page by page
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			TextExtractionStrategy strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
			// Extract the text
			String text = strategy.getResultantText();
			// Create a new paragraph in the word document, adding the extracted
			// text
			XWPFParagraph p = doc.createParagraph();
			XWPFRun run = p.createRun();
			run.setText(text);
			// Adding a page break
			run.addBreak(BreakType.PAGE);
		}
		// Write the word document
		FileOutputStream out = new FileOutputStream("src/output/pdf.docx");
		doc.write(out);
		// Close all open files
		out.close();
		reader.close();
		doc.close();
		System.out.println("Done.");
	}

}
