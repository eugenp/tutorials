package com.baeldung.loose;

import java.util.List;

public class MetadataCollector {
    private final FetchMetadata fetchMetadata;
    private final ExportMetadata exportMetadata;

    public MetadataCollector(FetchMetadata fetchMetadata, ExportMetadata exportMetadata) {
        this.fetchMetadata = fetchMetadata;
        this.exportMetadata = exportMetadata;
    }

    public void collectMetadata() {
        List<Object> metadata = fetchMetadata.fetchMetadata();
        exportMetadata.export(metadata);
    }

    public FetchMetadata getFetchMetadata() {
        return fetchMetadata;
    }

    public ExportMetadata getExportMetadata() {
        return exportMetadata;
    }
}
