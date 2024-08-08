package com.baeldung.checkedcollections;

import java.util.Collection;

class DataProcessor {

    public boolean checkPrefix(Collection<?> data) {
        boolean result = true;
        if (data != null) {
            for (Object item : data) {
                if (item != null && !((String) item).startsWith("DATA_")) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
