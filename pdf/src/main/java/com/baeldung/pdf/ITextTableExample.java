package com.baeldung.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ITextTableExample {
	public static void main(String[] args) throws IOException, DocumentException, URISyntaxException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));

		document.open();

		PdfPTable table = new PdfPTable(3);
		addTableHeader(table);
		addRows(table);
		addCustomRows(table);

		document.add(table);
		document.close();
	}

	private static void addCustomRows(PdfPTable table) throws URISyntaxException, BadElementException, IOException {
		Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
		Image img = Image.getInstance(path.toAbsolutePath().toString());
		img.scalePercent(10);

		PdfPCell imageCell = new PdfPCell(img);
		table.addCell(imageCell);

		PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
		horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(horizontalAlignCell);

		PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
		verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(verticalAlignCell);
	}

	private static void addRows(PdfPTable table) {
		table.addCell("row 1, col 1");
		table.addCell("row 1, col 2");
		table.addCell("row 1, col 3");
	}

	private static void addTableHeader(PdfPTable table) {
		Stream.of("column header 1", "column header 2", "column header 3")
				.forEach(columnTitle -> {
					PdfPCell header = new PdfPCell();
					header.setBackgroundColor(BaseColor.LIGHT_GRAY);
					header.setBorderWidth(2);
					header.setPhrase(new Phrase(columnTitle));
					table.addCell(header);
				});
	}
}
