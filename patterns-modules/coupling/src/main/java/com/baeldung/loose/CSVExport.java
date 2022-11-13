package com.baeldung.loose;

import java.io.File;
import java.util.List;

public class CSVExport implements ExportMetadata {
    @Override
    public File export(List<Object> metadata) {
        System.out.println("Exporting data...");
        // Export Metadata
        File outputCSV = null;
        return outputCSV;
    }
}
