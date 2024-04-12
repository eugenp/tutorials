package com.baeldung.loose;

import java.io.File;
import java.util.List;

public class PDFExport implements ExportMetadata {
    @Override
    public File export(List<Object> metadata) {
        System.out.println("PDF Export");
        // Some logic
        File outputPDF = null;
        return outputPDF;
    }
}
