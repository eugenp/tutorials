package com.baeldung.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfBoxImageExample {

	public static void main(String[] args) throws IOException, URISyntaxException {
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		PDImageXObject image = PDImageXObject.createFromFile(path.toAbsolutePath().toString(), document);
		contentStream.drawImage(image, 0, 0);
		contentStream.close();

		document.save("pdfBoxImage.pdf");
		document.close();
	}
}
