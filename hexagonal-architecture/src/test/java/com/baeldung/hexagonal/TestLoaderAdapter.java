package com.baeldung.hexagonal;

import org.junit.Assert;

public class TestLoaderAdapter implements LoaderPort {
    private String data;

    public void loadData(byte[] data) {
        this.data = new String(data);
    }

    public void assertDataLoaded(String expectedData) {
        Assert.assertEquals("Data loaded doesn't match with expected", expectedData, data);
    }
}
