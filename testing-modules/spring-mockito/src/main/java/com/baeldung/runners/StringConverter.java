package com.baeldung.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StringConverter {

    private final DataProvider dataProvider;

    @Autowired
    public StringConverter(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public List<String> convert() {
        return dataProvider.getValues().map(String::toUpperCase).collect(Collectors.toList());
    }
}
