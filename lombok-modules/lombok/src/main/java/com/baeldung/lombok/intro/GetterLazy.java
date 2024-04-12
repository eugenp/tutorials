package com.baeldung.lombok.intro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

public class GetterLazy {

    private static final String DELIMETER = ",";

    @Getter(lazy = true)
    private final Map<String, Long> transactions = getTransactions();

    private Map<String, Long> getTransactions() {

        final Map<String, Long> cache = new HashMap<>();
        List<String> txnRows = readTxnListFromFile();

        txnRows.forEach(s -> {
            String[] txnIdValueTuple = s.split(DELIMETER);
            cache.put(txnIdValueTuple[0], Long.parseLong(txnIdValueTuple[1]));
        });

        return cache;
    }

    private List<String> readTxnListFromFile() {

        // read large file
        return Stream.of("file content here").collect(Collectors.toList());
    }
}
