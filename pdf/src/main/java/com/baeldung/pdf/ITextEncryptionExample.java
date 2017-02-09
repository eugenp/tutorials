package com.baeldung.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class ITextEncryptionExample {
	public static void main(String[] args) throws DocumentException, IOException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));

		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk("Hello World", font);

		document.add(chunk);
		document.close();

		PdfReader pdfReader = new PdfReader("iTextHelloWorld.pdf");
		PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("iTextEncryptedPdf.pdf"));

		pdfStamper.setEncryption("userpass".getBytes(),
				"ownerpass".getBytes(), 0,
				PdfWriter.ENCRYPTION_AES_256);

		pdfStamper.close();
	}
}
