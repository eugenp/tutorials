package com.baeldung.reducingIfElse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OperatorFactory {

    static Map<String, Operation> operationMap = new HashMap<>();
    static {
        operationMap.put("add", new Addition());
        operationMap.put("divide", new Division());
        operationMap.put("multiply", new Multiplication());
        operationMap.put("subtract", new Subtraction());
        operationMap.put("modulo", new Modulo());
    }

    public static Optional<Operation> getOperation(String operation) {
        return Optional.ofNullable(operationMap.get(operation));
    }
}
