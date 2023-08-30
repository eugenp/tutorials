package com.baeldung.concurrent.futurevscompletablefuturevsrxjava;

public class TestObject {

    private int dataPointOne;
    private int dataPointTwo;

    public TestObject() {
        dataPointOne = 10;
    }

    public int getDataPointOne() {
        return dataPointOne;
    }

    public void setDataPointOne(int dataPointOne) {
        this.dataPointOne = dataPointOne;
    }

    public int getDataPointTwo() {
        return dataPointTwo;
    }

    public void setDataPointTwo(int dataPointTwo) {
        this.dataPointTwo = dataPointTwo;
    }

    @Override
    public String toString() {
        return "TestObject{" + "dataPointOne=" + dataPointOne + ", dataPointTwo=" + dataPointTwo + '}';
    }
}
