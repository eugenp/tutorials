package com.baeldung.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class PDF2ImageExample {

	private static final String PDF = "src/main/resources/pdf.pdf";
	private static final String JPG = "http://cdn2.baeldung.netdna-cdn.com/wp-content/uploads/2016/05/baeldung-rest-widget-main-1.2.0";
	private static final String GIF = "https://media.giphy.com/media/l3V0x6kdXUW9M4ONq/giphy";

	public static void main(String[] args) {
		try {
			generateImageFromPDF(PDF, "png");
			generateImageFromPDF(PDF, "jpeg");
			generateImageFromPDF(PDF, "gif");
			generatePDFFromImage(JPG, "jpg");
			generatePDFFromImage(GIF, "gif");
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}

	private static void generateImageFromPDF(String filename, String extension) throws IOException {
		PDDocument document = PDDocument.load(new File(filename));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		for (int page = 0; page < document.getNumberOfPages(); ++page) {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
			ImageIOUtil.writeImage(bim, String.format("src/output/pdf-%d.%s", page + 1, extension), 300);
		}
		document.close();
	}

	private static void generatePDFFromImage(String filename, String extension)
			throws IOException, BadElementException, DocumentException {
		Document document = new Document();
		String input = filename + "." + extension;
		String output = "src/output/" + extension + ".pdf";
		FileOutputStream fos = new FileOutputStream(output);
		PdfWriter writer = PdfWriter.getInstance(document, fos);
		writer.open();
		document.open();
		document.add(Image.getInstance((new URL(input))));
		document.close();
		writer.close();
	}

}
