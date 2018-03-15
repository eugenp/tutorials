
package com.baeldung.opencsv.examples;

import com.baeldung.opencsv.beans.CsvBean;
import com.baeldung.opencsv.beans.WriteExampleBean;
import com.baeldung.opencsv.helpers.Helpers;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.baeldung.opencsv.pojos.CsvTransfer;

import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

    public static CompletableFuture writeCsvUsingBeanBuilder(Path path) {
        try {
            Writer writer  = new FileWriter(path.toString());

            StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            List<CsvBean> list = new ArrayList<>();
            list.add(new WriteExampleBean("Test1", "sfdsf", "fdfd"));
            list.add(new WriteExampleBean("Test2", "ipso", "facto"));

            sbc.write(list);
            writer.close();

        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return CompletableFuture.completedFuture(Helpers.readFile(path));
    }

}