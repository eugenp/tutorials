package com.baeldung.exceltopdf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ExcelToPDFConverter {

    public static XSSFWorkbook readExcelFile(String excelFilePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(excelFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        inputStream.close();
        return workbook;
    }
    private static Document createPDFDocument(String pdfFilePath) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
        document.open();
        return document;
    }
    public static void convertExcelToPDF(String excelFilePath, String pdfFilePath) throws IOException, DocumentException {
        XSSFWorkbook workbook = readExcelFile(excelFilePath);
        Document document = createPDFDocument(pdfFilePath);

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            XSSFSheet worksheet = workbook.getSheetAt(i);

            // Add header with sheet name as title
            Paragraph title = new Paragraph(worksheet.getSheetName(), new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            createAndAddTable(worksheet, document);
            // Add a new page for each sheet (except the last one)
            if (i < workbook.getNumberOfSheets() - 1) {
                document.newPage();
            }
        }

        document.close();
        workbook.close();
    }

    private static void createAndAddTable(XSSFSheet worksheet, Document document) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(worksheet.getRow(0).getPhysicalNumberOfCells());
        table.setWidthPercentage(100);
        addTableHeader(worksheet, table);
        addTableData(worksheet, table);
        document.add(table);
    }

    private static void addTableHeader(XSSFSheet worksheet, PdfPTable table) throws DocumentException, IOException {
        Row headerRow = worksheet.getRow(0);
        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
            Cell cell = headerRow.getCell(i);
            String headerText = cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : String.valueOf(cell.getNumericCellValue());
            PdfPCell headerCell = new PdfPCell(new Phrase(headerText, getCellStyle(cell)));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell);
        }
    }

    private static void addTableData(XSSFSheet worksheet, PdfPTable table) throws DocumentException, IOException {
        Iterator<Row> rowIterator = worksheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0) {
                continue;
            }
            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                Cell cell = row.getCell(i);
                String cellValue;
                if (cell != null) {
                    if (cell.getCellType() == CellType.STRING) {
                        cellValue = cell.getStringCellValue();
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    } else {
                        cellValue = "";
                    }
                } else {
                    cellValue = "";
                }
                PdfPCell cellPdf = new PdfPCell(new Phrase(cellValue, getCellStyle(cell)));
                // Set background color
                short bgColorIndex = cell.getCellStyle()
                    .getFillForegroundColor();
                if (bgColorIndex != IndexedColors.AUTOMATIC.getIndex()) {
                    XSSFColor bgColor = (XSSFColor) cell.getCellStyle()
                        .getFillForegroundColorColor();
                    if (bgColor != null) {
                        byte[] rgb = bgColor.getRGB();
                        if (rgb != null && rgb.length == 3) {
                            cellPdf.setBackgroundColor(new BaseColor(rgb[0] & 0xFF, rgb[1] & 0xFF, rgb[2] & 0xFF));
                        }
                    }
                }

                cellPdf.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellPdf);
            }
        }
    }

    private static Font getCellStyle(Cell cell) throws DocumentException, IOException {
        Font font = new Font(Font.FontFamily.HELVETICA, 12);
        CellStyle cellStyle = cell.getCellStyle();
        org.apache.poi.ss.usermodel.Font cellFont = cell.getSheet().getWorkbook().getFontAt(cellStyle.getFontIndexAsInt());

        short fontColorIndex = cellFont.getColor();
        if (fontColorIndex != IndexedColors.AUTOMATIC.getIndex() && cellFont instanceof XSSFFont) {
            XSSFColor fontColor = ((XSSFFont) cellFont).getXSSFColor();
            if (fontColor != null) {
                byte[] rgb = fontColor.getRGB();
                if (rgb != null && rgb.length == 3) {
                    font.setColor(new BaseColor(rgb[0] & 0xFF, rgb[1] & 0xFF, rgb[2] & 0xFF));
                }
            }
        }
        if (cellFont.getItalic()) {
            font.setStyle(Font.ITALIC);
        }

        if (cellFont.getStrikeout()) {
            font.setStyle(Font.STRIKETHRU);
        }

        if (cellFont.getUnderline() == 1) {
            font.setStyle(Font.UNDERLINE);
        }

        short fontSize = cellFont.getFontHeightInPoints();
        font.setSize(fontSize);

        if (cellFont.getBold()) {
            font.setStyle(Font.BOLD);
        }
        return font;
    }

    public static void main(String[] args) throws DocumentException, IOException {
        String excelFilePath = "src/main/resources/excelsample.xlsx";
        String pdfFilePath = "src/main/resources/pdfsample.pdf";
        convertExcelToPDF(excelFilePath, pdfFilePath);
    }
}
