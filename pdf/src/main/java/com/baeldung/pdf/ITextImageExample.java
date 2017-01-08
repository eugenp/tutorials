package com.baeldung.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ITextImageExample {

	public static void main(String[] args) throws DocumentException, IOException, URISyntaxException {
		Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("iTextImageExample.pdf"));
		document.open();
		Image img = Image.getInstance(path.toAbsolutePath().toString());
		document.add(img);

		document.close();
	}
}
