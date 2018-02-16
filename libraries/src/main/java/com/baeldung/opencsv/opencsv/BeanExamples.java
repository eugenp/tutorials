package com.baeldung.opencsv.opencsv;

import com.baeldung.opencsv.helpers.Helpers;
import com.baeldung.opencsv.pojos.CsvBean;
import com.baeldung.opencsv.pojos.CsvTransfer;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BeanExamples {

    public static CompletableFuture<List<CsvBean>> beanBuilderExample(Path path, Class clazz) {

        CsvTransfer csvTransfer = new CsvTransfer();

        try {

            ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
            ms.setType(clazz);

            Reader reader = Files.newBufferedReader(path);

            CsvToBean bean = new CsvToBeanBuilder(reader)
                    .withType(clazz)
                    .withMappingStrategy(ms)
                    .build();

            csvTransfer.setCsvList(bean.parse());
            reader.close();

        } catch (Exception ex) {
            Helpers.err(ex);
        }

        return CompletableFuture.completedFuture(csvTransfer.getCsvList());
    }

}