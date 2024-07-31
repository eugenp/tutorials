package com.baeldung.jpa.stringcast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class QueryExecutor {

    public static List<String[]> executeNativeQueryNoCastCheck(String statement, EntityManager em) {
        Query query = em.createNativeQuery(statement);
        return query.getResultList();
    }

    public static List<String[]> executeNativeQueryWithCastCheck(String statement, EntityManager em) {
        Query query = em.createNativeQuery(statement);
        List results = query.getResultList();

        if (results.isEmpty()) {
            return new ArrayList<>();
        }

        if (results.get(0) instanceof String) {
            return ((List<String>) results).stream().map(s -> new String[] { s }).collect(Collectors.toList());
        } else {
            return (List<String[]>) results;
        }
    }

    public static <T> List<T> executeNativeQueryGeneric(String statement, String mapping, EntityManager em) {
        Query query = em.createNativeQuery(statement, mapping);
        return query.getResultList();
    }

}
