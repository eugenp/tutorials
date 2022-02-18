package com.baeldung.poi.excel.write.addimageincell;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This Helper class Add an Image to a Cell of an Excel File With apache-poi api.
 *
 */
public class ExcelCellImageHelper {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        try (final Workbook workbook = new XSSFWorkbook();
            FileOutputStream saveExcel = new FileOutputStream("target/baeldung-apachepoi.xlsx");) {
            
            Sheet sheet = workbook.createSheet("Avengers");

            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            XSSFClientAnchor ironManAnchor = new XSSFClientAnchor();
            XSSFClientAnchor spiderManAnchor = new XSSFClientAnchor();

            // Fill row1 data
            Row row1 = sheet.createRow(0);
            row1.setHeight((short) 1000);
            row1.createCell(0)
                .setCellValue("IRON-MAN");
            updateCellWithImage(workbook, 1, drawing, ironManAnchor, "ironman.png");

            // Fill row2 data
            Row row2 = sheet.createRow(1);
            row2.setHeight((short) 1000);
            row2.createCell(0)
                .setCellValue("SPIDER-MAN");
            updateCellWithImage(workbook, 2, drawing, spiderManAnchor, "spiderman.png");

            // Resize all columns to fit the content size
            for (int i = 0; i < 2; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(saveExcel);
        }

    }

    /**
     * This method position the anchor for a given rowNum and add the image correctly.
     * @param workbook
     * @param rowNum
     * @param drawing
     * @param inputImageAnchor
     * @throws IOException
     */
    private static void updateCellWithImage(Workbook workbook, int rowNum, XSSFDrawing drawing, XSSFClientAnchor inputImageAnchor, String inputImageName) throws IOException {
        InputStream inputImageStream = ExcelCellImageHelper.class.getClassLoader()
            .getResourceAsStream(inputImageName);
        byte[] inputImageBytes = IOUtils.toByteArray(inputImageStream);
        int inputImagePictureID = workbook.addPicture(inputImageBytes, Workbook.PICTURE_TYPE_PNG);
        inputImageStream.close();
        inputImageAnchor.setCol1(1);
        inputImageAnchor.setRow1(rowNum - 1);
        inputImageAnchor.setCol2(2);
        inputImageAnchor.setRow2(rowNum);
        drawing.createPicture(inputImageAnchor, inputImagePictureID);
    }

}
