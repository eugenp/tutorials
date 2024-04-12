package com.baeldung.loose;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LooselyCouplingUnitTest {

    @Test
    public void givenMetadataCollector_thenCollectMetadataXMLAndExportCSV() {
        FetchMetadata metadata = new XMLFetch();
        ExportMetadata exportMetadata = new CSVExport();
        MetadataCollector collector = new MetadataCollector(metadata, exportMetadata);
        collector.collectMetadata();
        assertTrue(collector.getExportMetadata() instanceof CSVExport);
        assertTrue(collector.getFetchMetadata() instanceof XMLFetch);
    }

    @Test
    public void givenMetadataCollector_thenCollectMetadataUsingJSONAndExportPDF() {
        FetchMetadata metadata = new JSONFetch();
        ExportMetadata exportMetadata = new PDFExport();
        MetadataCollector collector = new MetadataCollector(metadata, exportMetadata);
        collector.collectMetadata();
        assertTrue(collector.getExportMetadata() instanceof PDFExport);
        assertTrue(collector.getFetchMetadata() instanceof JSONFetch);
    }

    @Test
    public void givenMetadataCollector_thenCollectMetadataUsingXMLAndExportPDF() {
        FetchMetadata metadata = new XMLFetch();
        ExportMetadata exportMetadata = new PDFExport();
        MetadataCollector collector = new MetadataCollector(metadata, exportMetadata);
        collector.collectMetadata();
        assertTrue(collector.getExportMetadata() instanceof PDFExport);
        assertTrue(collector.getFetchMetadata() instanceof XMLFetch);
    }
}