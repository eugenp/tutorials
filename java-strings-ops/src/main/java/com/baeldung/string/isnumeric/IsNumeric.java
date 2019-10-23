package com.baeldung.string.isnumeric;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class IsNumeric {
    public boolean usingCoreJava(String strNum) {
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public boolean usingRegularExpressions(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
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
