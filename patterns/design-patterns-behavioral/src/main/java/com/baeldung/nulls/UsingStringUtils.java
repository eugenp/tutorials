package com.baeldung.nulls;

import org.apache.commons.lang3.StringUtils;

public class UsingStringUtils {

    public void accept(String param) {
        if (StringUtils.isNotEmpty(param)) {
            System.out.println(param);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void acceptOnlyNonBlank(String param) {
        if (StringUtils.isNotBlank(param)) {
            System.out.println(param);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
