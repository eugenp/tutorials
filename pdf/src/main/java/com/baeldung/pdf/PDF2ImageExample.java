package com.baeldung.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class PDF2ImageExample {

	private static final String FILENAME = "src/main/resources/pdf.pdf";
	
	public static void main(String[] args) {
		try {
			generateImageFromPDF(FILENAME, "png");
			generateImageFromPDF(FILENAME, "jpeg");
			generateImageFromPDF(FILENAME, "gif");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generateImageFromPDF(String filename, String extension) throws IOException {
		System.out.println("Creating " + extension + " image from a PDF file: " + filename);
		PDDocument document = PDDocument.load(new File(filename));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		for (int page = 0; page < document.getNumberOfPages(); ++page) {
			System.out.println("Page number: " + (page + 1) + " is being rendered");
			BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
			ImageIOUtil.writeImage(bim, "src/output/pdf" + "-" + (page + 1) + "." + extension, 300);
		}
		document.close();
		System.out.println("Done.");
	}
}
