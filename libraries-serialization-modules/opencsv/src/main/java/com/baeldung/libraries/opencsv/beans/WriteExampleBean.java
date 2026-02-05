package com.baeldung.libraries.opencsv.beans;

public class WriteExampleBean extends CsvBean {

    private String colA;

    private String colB;

    private String colC;

    public WriteExampleBean(String colA, String colB, String colC) {
        this.colA = colA;
        this.colB = colB;
        this.colC = colC;
    }

    public String getColA() {
        return colA;
    }

    public void setColA(String colA) {
        this.colA = colA;
    }

    public String getColB() {
        return colB;
    }

    public void setColB(String colB) {
        this.colB = colB;
    }

    public String getColC() {
        return colC;
    }

    public void setColC(String colC) {
        this.colC = colC;
    }

    @Override
    public String toString() {
        return colA + ',' + colB + "," + colC;
    }
}
