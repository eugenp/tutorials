package com.baeldung.hexagonal;

import org.junit.Test;

public class MyEtlServiceTest {

    @Test
    public void givenInputData_thenLoadTransformedData() {
        String inputData = "This is an example Input with some numbers(1,2,3,4) and some symbols";
        String expectedLoadedData = "THIS IS AN EXAMPLE INPUT WITH SOME NUMBERS(1,2,3,4) AND SOME SYMBOLS";

        TestExtractorAdapter extractorAdapter = new TestExtractorAdapter(inputData);
        TestLoaderAdapter loaderAdapter = new TestLoaderAdapter();

        MyEtlService tested = new MyEtlService();
        tested.etl(extractorAdapter, loaderAdapter);
        loaderAdapter.assertDataLoaded(expectedLoadedData);
    }
}
