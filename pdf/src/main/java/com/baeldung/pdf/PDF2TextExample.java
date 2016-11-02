package com.baeldung.pdf;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDF2TextExample {

	private static final String FILENAME = "src/main/resources/pdf.pdf";

	public static void main(String[] args) {
		try {
			generateTxtFromPDF(FILENAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generateTxtFromPDF(String filename) throws IOException {
		System.out.println("Parsing text from PDF file " + filename);
		String parsedText = null;
		PDFTextStripper pdfStripper;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;

		File f = new File(filename);
		PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));

		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (Exception e) {
			System.err.println("An exception occured in parsing the PDF Document.");
			e.printStackTrace();
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		System.out.println("Writing PDF text to output text file");
		try {
			PrintWriter pw = new PrintWriter("src/output/pdf.txt");
			pw.print(parsedText);
			pw.close();
		} catch (Exception e) {
			System.out.println("An exception occured in writing the pdf text to file.");
			e.printStackTrace();
		}
		System.out.println("Done.");
	}

}
