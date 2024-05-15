package com.baeldung.java.io.pojotocsv;

import java.util.Arrays;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class CustomHeaderStrategy<T> extends HeaderColumnNameMappingStrategy<T> {
    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        String[] header = super.generateHeader(bean);
        return Arrays.stream(header)
            .map(String::toLowerCase)
            .toArray(String[]::new);
    }
}
