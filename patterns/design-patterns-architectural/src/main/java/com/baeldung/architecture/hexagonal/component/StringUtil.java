package com.baeldung.architecture.hexagonal.component;

public interface StringUtil {
    static StringUtil create(StringTransformationLogger stringTransformationLoggers) {
        return new StringUtilImpl(stringTransformationLoggers);
    }

    String toUpperCase(String string);
}
