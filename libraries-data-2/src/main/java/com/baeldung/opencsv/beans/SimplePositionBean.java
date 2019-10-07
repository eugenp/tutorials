package com.baeldung.opencsv.beans;

import com.opencsv.bean.CsvBindByPosition;

public class SimplePositionBean extends CsvBean {

    @CsvBindByPosition(position = 0)
    private String exampleColOne;

    @CsvBindByPosition(position = 1)
    private String exampleColTwo;

    public String getExampleColOne() {
        return exampleColOne;
    }

    private void setExampleColOne(String exampleColOne) {
        this.exampleColOne = exampleColOne;
    }

    public String getExampleColTwo() {
        return exampleColTwo;
    }

    private void setExampleCsvTwo (String exampleColTwo) {
        this.exampleColTwo = exampleColTwo;
    }

}
