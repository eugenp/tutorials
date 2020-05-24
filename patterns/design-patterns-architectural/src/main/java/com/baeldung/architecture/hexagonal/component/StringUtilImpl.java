package com.baeldung.architecture.hexagonal.component;

import java.util.Date;

class StringUtilImpl implements StringUtil {
    private StringTransformationLogger stringTransformationLogger;

    StringUtilImpl(StringTransformationLogger stringTransformationLogger) {
        this.stringTransformationLogger = stringTransformationLogger;
    }

    @Override
    public String toUpperCase(String string) {
        String transformed = string.toUpperCase();
        stringTransformationLogger.logTransformation(new Date(), string, transformed);
        return transformed;
    }
}
