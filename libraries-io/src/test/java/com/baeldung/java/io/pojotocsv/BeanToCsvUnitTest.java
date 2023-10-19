package com.baeldung.java.io.pojotocsv;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BeanToCsvUnitTest {

    List<Application> applications = new ArrayList<>();
    List<ApplicationWithAnnotation> applicationsWithAnnotation = new ArrayList<>();

    @BeforeEach
    public void beforeEach() {
        applications = List.of(new Application("123", "Sam", 34, "2023-08-11"), new Application("456", "Tam", 44, "2023-02-11"), new Application("890", "Jam", 54, "2023-03-11"));

        applicationsWithAnnotation = List.of(new ApplicationWithAnnotation("123", "Sam", 34, "2023-08-11"), new ApplicationWithAnnotation("456", "Tam", 44, "2023-02-11"), new ApplicationWithAnnotation("789", "Jam", 54, "2023-03-11"));
    }

    @Test
    public void givenApplicationPOJO_whenUsingDefaultStrategy_thenReceiveCSVFormatWithAscendingOrderOfField() throws Exception {
        BeanToCsv beanToCsv = new BeanToCsv();
        beanToCsv.beanToCSVWithDefault(applications);
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of("src/main/resources/application.csv"))) {
            List<String> content = bufferedReader.lines()
                .toList();
            assertThat(content.get(0)).isEqualTo("AGE,CREATED_AT,ID,NAME");
            assertThat(content.get(1)).isEqualTo("34,2023-08-11,123,Sam");
            assertThat(content.get(2)).isEqualTo("44,2023-02-11,456,Tam");
            assertThat(content.get(3)).isEqualTo("54,2023-03-11,890,Jam");
        }
    }

    @Test
    public void givenApplicationPOJO_whenUsingCustomHeaderStrategy_thenReceiveCSVFormatWithCustomHeaders() throws Exception {
        BeanToCsv beanToCsv = new BeanToCsv();
        beanToCsv.beanToCSVWithCustomHeaderStrategy(applications);
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of("src/main/resources/application2.csv"))) {
            List<String> content = bufferedReader.lines()
                .toList();
            assertThat(content.get(0)).isEqualTo("age,created_at,id,name");
            assertThat(content.get(1)).isEqualTo("34,2023-08-11,123,Sam");
            assertThat(content.get(2)).isEqualTo("44,2023-02-11,456,Tam");
            assertThat(content.get(3)).isEqualTo("54,2023-03-11,890,Jam");
        }
    }

    @Test
    public void givenApplicationPOJOWithAnnotation_whenUsingCustomPositionStrategy_thenReceiveCSVFormatWithCustomPosition() throws Exception {
        BeanToCsv beanToCsv = new BeanToCsv();
        beanToCsv.beanToCSVWithCustomPositionStrategy(applicationsWithAnnotation);
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of("src/main/resources/application3.csv"))) {
            List<String> content = bufferedReader.lines()
                .toList();
            assertThat(content.get(0)).isEqualTo("Sam,123,34,2023-08-11");
            assertThat(content.get(1)).isEqualTo("Tam,456,44,2023-02-11");
            assertThat(content.get(2)).isEqualTo("Jam,789,54,2023-03-11");
        }
    }

    @Test
    public void givenApplicationPOJOWithAnnotation_whenUsingCustomHeaderPositionStrategy_thenReceiveCSVFormatWithCustomHeaderPosition() throws Exception {
        BeanToCsv beanToCsv = new BeanToCsv();
        beanToCsv.beanToCSVWithCustomHeaderAndPositionStrategy(applicationsWithAnnotation);
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of("src/main/resources/application4.csv"))) {
            List<String> content = bufferedReader.lines()
                .toList();
            assertThat(content.get(0)).isEqualTo("name,id,age,created_at");
            assertThat(content.get(1)).isEqualTo("Sam,123,34,2023-08-11");
            assertThat(content.get(2)).isEqualTo("Tam,456,44,2023-02-11");
            assertThat(content.get(3)).isEqualTo("Jam,789,54,2023-03-11");
        }
    }
}
