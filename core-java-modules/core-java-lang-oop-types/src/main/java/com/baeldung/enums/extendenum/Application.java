package com.baeldung.enums.extendenum;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class Application {
    private static final Map<ImmutableOperation, Operator> OPERATION_MAP;

    static {
        OPERATION_MAP = new EnumMap<>(ImmutableOperation.class);
        OPERATION_MAP.put(ImmutableOperation.TO_LOWER, String::toLowerCase);
        OPERATION_MAP.put(ImmutableOperation.INVERT_CASE, StringUtils::swapCase);
        OPERATION_MAP.put(ImmutableOperation.REMOVE_WHITESPACES, input -> input.replaceAll("\\s", ""));

        if (Arrays.stream(ImmutableOperation.values()).anyMatch(it -> !OPERATION_MAP.containsKey(it))) {
            throw new IllegalStateException("Unmapped enum constant found!");
        }
    }

    public String applyImmutableOperation(ImmutableOperation operation, String input) {
        return OPERATION_MAP.get(operation).apply(input);
    }

    public String getDescription(StringOperation stringOperation) {
        return stringOperation.getDescription();
    }

    public String applyOperation(StringOperation operation, String input) {
        return operation.apply(input);
    }

}
