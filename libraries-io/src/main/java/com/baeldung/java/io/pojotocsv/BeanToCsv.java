package com.baeldung.java.io.pojotocsv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class BeanToCsv {

    public void beanToCSVWithDefault(List<Application> applications) throws Exception {
        try (FileWriter writer = new FileWriter("src/main/resources/application.csv")) {
            var builder = new StatefulBeanToCsvBuilder<Application>(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(',')
                .build();
            builder.write(applications);
        }
    }

    public void beanToCSVWithCustomHeaderStrategy(List<Application> applications) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (FileWriter writer = new FileWriter("src/main/resources/application2.csv")) {
            var mappingStrategy = new CustomHeaderStrategy<Application>();
            mappingStrategy.setType(Application.class);

            var builder = new StatefulBeanToCsvBuilder<Application>(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withMappingStrategy(mappingStrategy)
                .build();
            builder.write(applications);
        }
    }

    public void beanToCSVWithCustomPositionStrategy(List<ApplicationWithAnnotation> applications) throws Exception {
        try (FileWriter writer = new FileWriter("src/main/resources/application3.csv")) {
            var builder = new StatefulBeanToCsvBuilder<ApplicationWithAnnotation>(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();
            builder.write(applications);
        }
    }

    public void beanToCSVWithCustomHeaderAndPositionStrategy(List<ApplicationWithAnnotation> applications) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (FileWriter writer = new FileWriter("src/main/resources/application4.csv")) {
            var mappingStrategy = new CustomColumnPositionStrategy<ApplicationWithAnnotation>();
            mappingStrategy.setType(ApplicationWithAnnotation.class);

            var builder = new StatefulBeanToCsvBuilder<ApplicationWithAnnotation>(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withMappingStrategy(mappingStrategy)
                .build();
            builder.write(applications);
        }
    }

}
