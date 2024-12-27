package com.baeldung.elasticsearch.importcsv;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CsvImportService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    public void importCSV(File file) {
        try (Reader reader = new FileReader(file)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("id", "name", "category", "price", "stock")
                .withFirstRecordAsHeader()
                .parse(reader);
            for (CSVRecord record : records) {
                IndexRequest request = new IndexRequest("products").id(record.get("id"))
                    .source(Map.of("name", record.get("name"), "category", record.get("category"), "price", Double.parseDouble(record.get("price")), "stock",
                        Integer.parseInt(record.get("stock"))));
                restHighLevelClient.index(request, RequestOptions.DEFAULT);
            }
        } catch (Exception e) {
            // handle exception
        }
    }

}

