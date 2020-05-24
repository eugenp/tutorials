package com.baeldung.architecture.hexagonal.callers;

import java.util.Arrays;
import java.util.List;

import com.baeldung.architecture.hexagonal.component.StringUtil;

public class StringArrayUppercaseConverterToStringUtilAdapter {
    private final StringUtil stringUtil;

    public StringArrayUppercaseConverterToStringUtilAdapter(StringUtil stringUtil) {
        this.stringUtil = stringUtil;
    }

    public String[] convertToUppercase(String[] strings) {
        String upperCase = stringUtil.toUpperCase(String.join("", strings));
        return upperCase.split("");
    }
}
