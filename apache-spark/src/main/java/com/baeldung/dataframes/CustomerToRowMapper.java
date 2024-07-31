package com.baeldung.dataframes;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;

public class CustomerToRowMapper implements MapFunction<Customer, Row> {

    @Override
    public Row call(Customer customer) {
        Row row = RowFactory.create(
            customer.getId(), customer.getName().toUpperCase(),
            StringUtils.substring(customer.getGender(),0, 1),
            customer.getTransaction_amount());
        return row;
    }
}