package com.baeldung.dataframes;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

class RowToCustomerMapper implements MapFunction<Row, Customer> {

    @Override
    public Customer call(Row row) {

        Customer customer = new Customer();
        customer.setId(row.getAs("id"));
        customer.setName(row.getAs("name"));
        customer.setGender(row.getAs("gender"));
        customer.setTransaction_amount(Math.toIntExact(row.getAs("transaction_amount")));

        return customer;
    }
}
