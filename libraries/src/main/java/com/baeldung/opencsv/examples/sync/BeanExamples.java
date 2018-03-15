package com.baeldung.opencsv.examples.sync;

import com.baeldung.opencsv.beans.CsvBean;
import com.baeldung.opencsv.beans.WriteExampleBean;
import com.baeldung.opencsv.helpers.Helpers;
import com.baeldung.opencsv.pojos.CsvTransfer;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;

import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BeanExamples {

    public static List<CsvBean> beanBuilderExample(Path path, Class clazz) {
        CsvTransfer csvTransfer = new CsvTransfer();
        try {
            ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
            ms.setType(clazz);

            Reader reader = Files.newBufferedReader(path);
            CsvToBean cb = new CsvToBeanBuilder(reader)
                    .withType(clazz)
                    .withMappingStrategy(ms)
                    .build();

            csvTransfer.setCsvList(cb.parse());
            reader.close();

        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return csvTransfer.getCsvList();
    }

    public static String writeCsvFromBean(Path path) {
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
        return Helpers.readFile(path);
    }
}