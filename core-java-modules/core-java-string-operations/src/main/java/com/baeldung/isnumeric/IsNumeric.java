package com.baeldung.isnumeric;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class IsNumeric {
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean usingCoreJava(String strNum) {
        if (strNum == null) {
            return false;
        }

        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean usingPreCompiledRegularExpressions(String strNum) {
        if (strNum == null) {
            return false;
        }

        return pattern.matcher(strNum)
            .matches();
    }

    public boolean usingNumberUtils_isCreatable(String strNum) {
        return NumberUtils.isCreatable(strNum);
    }

    public boolean usingNumberUtils_isParsable(String strNum) {
        return NumberUtils.isParsable(strNum);
    }

    public boolean usingStringUtils_isNumeric(String strNum) {
        return StringUtils.isNumeric(strNum);
    }

    public boolean usingStringUtils_isNumericSpace(String strNum) {
        return StringUtils.isNumericSpace(strNum);
    }
}
