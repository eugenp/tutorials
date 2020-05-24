package com.baeldung.architecture.hexagonal.callers;

public class StringArrayUppercaseConverter {
    private final StringArrayUppercaseConverterToStringUtilAdapter stringArrayUppercaseConverterToStringUtilAdapter;

    public StringArrayUppercaseConverter(StringArrayUppercaseConverterToStringUtilAdapter stringArrayUppercaseConverterToStringUtilAdapter) {
        this.stringArrayUppercaseConverterToStringUtilAdapter = stringArrayUppercaseConverterToStringUtilAdapter;
    }

    public String[] getUppercase(String[] strings) {
        return stringArrayUppercaseConverterToStringUtilAdapter.convertToUppercase(strings);
    }
}
