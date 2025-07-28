package com.baeldung.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFSampleMain {

    public static void main(String[] args) {
        
        try {
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));

            document.open();

            Document document2 = new Document();
            PdfWriter.getInstance(document2, new FileOutputStream("iTextParagraph.pdf"));

            document2.open();
            
            addParagraphInCenter(document2);
            
            PdfPTable table = new PdfPTable(3);
            addTableHeader(table);
            setAbsoluteColumnWidths(table);
            //setAbsoluteColumnWidthsInTableWidth(table);
            //setRelativeColumnWidths(table);
            addRows(table);
            addCustomRows(table);

            document.add(table);
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addParagraphInCenter(Document document) throws IOException, DocumentException {
        Paragraph paragraph = new Paragraph("This paragraph will be horizontally centered.");
        paragraph.setAlignment(Element.ALIGN_CENTER);  
        document.add(paragraph);
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

    private static void setAbsoluteColumnWidths(PdfPTable table) throws DocumentException {
       table.setTotalWidth(500); // Sets total table width to 500 points
       table.setLockedWidth(true);
       float[] columnWidths = {100f, 200f, 200f}; // Defines three columns with absolute widths
       table.setWidths(columnWidths);
    }

    private static void setAbsoluteColumnWidthsInTableWidth(PdfPTable table) throws DocumentException {
       table.setTotalWidth(new float[] {72f, 144f, 216f}); // First column 1 inch, second 2 inches, third 3 inches 
       table.setLockedWidth(true);
    }

    private static void setRelativeColumnWidths(PdfPTable table) throws DocumentException {
        // Set column widths (relative)
        table.setWidths(new float[] {1, 2, 1});
        table.setWidthPercentage(80); // Table width as 80% of page width
    }
    
    private static void addRows(PdfPTable table) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
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
}
