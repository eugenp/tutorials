package com.baeldung.hssfworkbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class ExcelController {

    @GetMapping("/download-excel")
    public ResponseEntity<byte[]> downloadExcel() {
        try {
            HSSFWorkbook workbook = ExcelCreator.createSampleWorkbook();

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                workbook.write(baos);
                byte[] bytes = baos.toByteArray();

                return ResponseEntity.ok()
                  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employee_data.xls")
                  .contentType(MediaType.parseMediaType("application/vnd.ms-excel")) // More specific MIME type
                  .body(bytes);
            }
        } catch (IOException e) {
            System.err.println("Error generating or writing Excel workbook: " + e.getMessage());
            return ResponseEntity.internalServerError()
              .build();
        }
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
              .body("File is empty. Please upload a file.");
        }

        try {
            try (HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream())) {
                Sheet sheet = workbook.getSheetAt(0);

                return ResponseEntity.ok("Sheet '" + sheet.getSheetName() + "' uploaded successfully!");
            }
        } catch (IOException e) {
            System.err.println("Error processing uploaded Excel file: " + e.getMessage());
            return ResponseEntity.internalServerError()
              .body("Failed to process the Excel file.");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during file upload: " + e.getMessage());
            return ResponseEntity.internalServerError()
              .body("An unexpected error occurred.");
        }
    }
}