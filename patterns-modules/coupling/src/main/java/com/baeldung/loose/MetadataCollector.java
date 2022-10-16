package com.baeldung.loose;

import java.util.List;

public class MetadataCollector {
    private FetchMetadata fetchMetadata;
    private ExportMetadata exportMetadata;

    public MetadataCollector(FetchMetadata fetchMetadata, ExportMetadata exportMetadata) {
        this.fetchMetadata = fetchMetadata;
        this.exportMetadata = exportMetadata;
    }

    public void collectMetadata() {
        List<Object> metadata = fetchMetadata.fetchMetadata();
        exportMetadata.export(metadata);
    }
}
