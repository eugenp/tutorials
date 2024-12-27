package com.baeldung.elasticsearch.importcsv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CsvImportServiceUnitTest {

    @Mock
    private RestHighLevelClient restHighLevelClient;
    @InjectMocks
    private CsvImportService csvImportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenCSV_usingLooping_returnIndexesRecordsSuccess() throws Exception {
        File csvFile = Paths.get("src", "test", "resources", "products.csv").toFile();
        csvImportService.importCSV(csvFile);

        // Capture the IndexRequest sent to the RestHighLevelClient
        ArgumentCaptor<IndexRequest> captor = ArgumentCaptor.forClass(IndexRequest.class);
        verify(restHighLevelClient, times(25)).index(captor.capture(), eq(RequestOptions.DEFAULT));

        // Verify the data in the IndexRequests
        IndexRequest firstRequest = captor.getAllValues().get(0);
        IndexRequest secondRequest = captor.getAllValues().get(1);

        assertEquals("1", firstRequest.id());
        assertEquals(Map.of(
            "name", "Microwave",
            "category", "Appliances",
            "price", 705.77,
            "stock", 136
        ), firstRequest.sourceAsMap());

        assertEquals("2", secondRequest.id());
        assertEquals(Map.of(
            "name", "Vacuum Cleaner",
            "category", "Appliances",
            "price", 1397.23,
            "stock", 92
        ), secondRequest.sourceAsMap());
    }
}
