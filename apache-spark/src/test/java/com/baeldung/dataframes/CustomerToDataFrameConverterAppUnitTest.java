package com.baeldung.dataframes;

import static com.baeldung.dataframes.CustomerToDataFrameConverterApp.convertAfterMappingRows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.Test;

class CustomerToDataFrameConverterAppUnitTest {

    @Test
    void givenCustomers_whenConvertAfterMappingRows_thenConvertsToDataSet() {

       List<Customer> customers = Arrays.asList(
            new Customer("01", "jo", "Female", 2000),
            new Customer("02", "jack", "Male", 1200)
       );

        Dataset<Row> customerDF = convertAfterMappingRows(customers);
        List<Row> rows = customerDF.collectAsList();
        Row row1 = rows.get(0);
        Row row2 = rows.get(1);

        assertEquals("01", row1.get(0));
        assertEquals( "JO", row1.get(1));
        assertEquals( "F", row1.get(2));
        assertEquals( 2000, row1.get(3));

        assertEquals("02", row2.get(0));
        assertEquals( "JACK", row2.get(1));
        assertEquals( "M", row2.get(2));
        assertEquals( 1200, row2.get(3));
    }

    @Test
    void givenCustomers_whenConvertWithNoChange_thenConvertsToDataSet() {

        List<Customer> customers = Arrays.asList(
            new Customer("01", "jo", "Female", 2000),
            new Customer("02", "jack", "Male", 1200)
        );

        Dataset<Row> customerDF = CustomerToDataFrameConverterApp.convertToDataFrameWithNoChange();
        List<Row> rows = customerDF.collectAsList();
        Row row1 = rows.get(0);
        Row row2 = rows.get(1);

        assertEquals("01", row1.getAs("id"));
        assertEquals( "jo", row1.getAs("name"));
        assertEquals( "Female", row1.getAs("gender"));
        assertEquals( 2000, (int)row1.getAs("transaction_amount"));

        assertEquals("02", row2.getAs("id"));
        assertEquals( "jack", row2.getAs("name"));
        assertEquals( "Male", row2.getAs("gender"));
        assertEquals( 1200, (int)row2.getAs("transaction_amount"));
    }
}
