package com.baeldung.batch.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.baeldung.batch.model.Transaction;

public class RecordFieldSetMapper implements FieldSetMapper<Transaction> {

    public Transaction mapFieldSet(FieldSet fieldSet) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyy");

        Transaction transaction = new Transaction();
        // you can either use the indices or custom names
        // I personally prefer the custom names easy for debugging and
        // validating the pipelines
        transaction.setUsername(fieldSet.readString("username"));
        transaction.setUserId(fieldSet.readInt("userid"));
        transaction.setAmount(fieldSet.readDouble(3));

        // Converting the date
        String dateString = fieldSet.readString(2);
        transaction.setTransactionDate(LocalDate.parse(dateString, formatter).atStartOfDay());

        return transaction;

    }

}
