package com.baeldung.pdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDF2TextExample {

	private static final String PDF = "src/main/resources/pdf.pdf";
	private static final String TXT = "src/main/resources/txt.txt";

	public static void main(String[] args) {
		try {
			generateTxtFromPDF(PDF);
			generatePDFFromTxt(TXT);
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}

	private static void generateTxtFromPDF(String filename) throws IOException {
		File f = new File(filename);
		String parsedText;
		PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
		parser.parse();

		COSDocument cosDoc = parser.getDocument();

		PDFTextStripper pdfStripper = new PDFTextStripper();
		PDDocument pdDoc = new PDDocument(cosDoc);

		parsedText = pdfStripper.getText(pdDoc);

		if (cosDoc != null)
			cosDoc.close();
		if (pdDoc != null)
			pdDoc.close();

		PrintWriter pw = new PrintWriter("src/output/pdf.txt");
		pw.print(parsedText);
		pw.close();
	}

	private static void generatePDFFromTxt(String filename) throws IOException, DocumentException {
		Document pdfDoc = new Document(PageSize.A4);
		PdfWriter.getInstance(pdfDoc, new FileOutputStream("src/output/txt.pdf"))
				.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
		pdfDoc.open();
		
		Font myfont = new Font();
		myfont.setStyle(Font.NORMAL);
		myfont.setSize(11);
		pdfDoc.add(new Paragraph("\n"));
		
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			Paragraph para = new Paragraph(strLine + "\n", myfont);
			para.setAlignment(Element.ALIGN_JUSTIFIED);
			pdfDoc.add(para);
		}
		
		pdfDoc.close();
		br.close();
	}

}
