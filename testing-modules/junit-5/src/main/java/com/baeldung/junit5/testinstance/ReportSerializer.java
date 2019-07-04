package com.baeldung.junit5.testinstance;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ReportSerializer {

    private static final int MAX_REPORT_SIZE = 65535;
    private static final int MIN_REPORT_SIZE = 200;
    private final Report report;

    public ReportSerializer(Report report) {
        this.report = report;
    }

    public byte[] serialize() throws IOException {
        byte[] reportContent = serializeReport();
        int totalLength = reportContent.length;
        validateSizeOfReport(totalLength);
        return reportContent;
    }

    private void validateSizeOfReport(int reportSize) {
        if (reportSize > MAX_REPORT_SIZE) {
            throw new ReportException("Report is too large");
        }
        if (reportSize < MIN_REPORT_SIZE) {
            throw new ReportException("Report is too small");
        }
    }

    private byte[] serializeReport() throws IOException {
        byte[] content;

        try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {
            try (ObjectOutputStream objectStream = new ObjectOutputStream(arrayOutputStream)) {
                objectStream.writeObject(report);
            }
            content = arrayOutputStream.toByteArray();
        }

        return content;
    }

}
