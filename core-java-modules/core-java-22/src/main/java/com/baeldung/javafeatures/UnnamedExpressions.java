package com.baeldung.javafeatures;

import static java.lang.StringTemplate.STR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class UnnamedExpressions {
    private Logger LOGGER = Logger.getLogger(String.valueOf(UnnamedExpressions.class));

    public int unnamedExpressionExampleUsingException(int someNumber) {
        int divided = 0;
        try {
            divided = someNumber / 0;
        } catch (ArithmeticException _) {
            System.err.println("Division by zero");
        }
        return divided;
    }

    public Object unnamedExpressionsExampleUsingSwitch(Object obj) {
        switch (obj) {
            case Integer _ -> System.out.println("Is an integer");
            case Float _ -> System.out.println("Is a float");
            case String _ -> System.out.println("Is a String");
            default -> System.out.println("Default");
        }
        ;
        return obj;
    }

    public boolean unnamedExpressionForTryWithResources() {
        String url = "localhost";
        String user = "user";
        String pwd = "password";
        try (Connection _ = DriverManager.getConnection(url, user, pwd)) {
            LOGGER.info(STR."""
                        DB Connection successful
                        URL = \{url}
                        usr = \{user}
                        pwd = \{pwd}""");
        } catch (SQLException e) {
            LOGGER.warning("Exception");
        }
        return true;
    }

    public Map<String, List<String>> unnamedExpressionForLambda() {
        Map<String, List<String>> map = new HashMap<>();
        map.computeIfAbsent("v1", _ -> new ArrayList<>())
          .add("0.1");
        return map;
    }

}

