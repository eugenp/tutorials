package com.baeldung.hexagonal;

public class MyEtlService {
    public void etl(ExtractorPort extractorPort, LoaderPort loaderPort) {
        byte[] data = extractorPort.extractData();

        String stringData = new String(data);

        String transformedData = transform(stringData);

        loaderPort.loadData(transformedData.getBytes());
    }

    private String transform(String data) {
        return data.toUpperCase();
    }
}
