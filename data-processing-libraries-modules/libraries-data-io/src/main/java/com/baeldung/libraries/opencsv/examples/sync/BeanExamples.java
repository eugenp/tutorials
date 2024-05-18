package com.baeldung.libraries.opencsv.examples.sync;

import com.baeldung.libraries.opencsv.beans.CsvBean;
import com.baeldung.libraries.opencsv.beans.WriteExampleBean;
import com.baeldung.libraries.opencsv.helpers.Helpers;
import com.baeldung.libraries.opencsv.pojos.CsvTransfer;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class BeanExamples {

    public static List<CsvBean> beanBuilderExample(Path path, Class<? extends CsvBean> clazz) throws Exception {
        CsvTransfer csvTransfer = new CsvTransfer();
        try (Reader reader = Files.newBufferedReader(path)) {
            CsvToBean<CsvBean> cb = new CsvToBeanBuilder<CsvBean>(reader)
              .withType(clazz)
              .build();

            csvTransfer.setCsvList(cb.parse());
        }

        return csvTransfer.getCsvList();
    }

    public static String writeCsvFromBean(Path path) throws Exception {

        List<CsvBean> sampleData = Arrays.asList(
            new WriteExampleBean("Test1", "sample", "data"),
            new WriteExampleBean("Test2", "ipso", "facto")
        );

        try (Writer writer = new FileWriter(path.toString())) {
            StatefulBeanToCsv<CsvBean> sbc = new StatefulBeanToCsvBuilder<CsvBean>(writer)
              .withQuotechar('\'')
              .build();

            sbc.write(sampleData);
        }

        return Helpers.readFile(path);
    }
}