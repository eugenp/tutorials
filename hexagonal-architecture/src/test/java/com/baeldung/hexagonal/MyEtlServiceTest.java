package com.baeldung.hexagonal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MyEtlServiceTest {

    @Mock
    private ExtractorPort extractorMock;

    @Mock
    private LoaderPort loaderMock;

    @Test
    public void givenInputData_thenLoadTransformedData() {
        String inputData = "This is an example Input with some numbers(1,2,3,4) and some symbols";
        String expectedLoadedData = "THIS IS AN EXAMPLE INPUT WITH SOME NUMBERS(1,2,3,4) AND SOME SYMBOLS";

        when(extractorMock.extractData()).thenReturn(inputData.getBytes());

        MyEtlService tested = new MyEtlService();
        tested.etl(extractorMock, loaderMock);

        ArgumentCaptor<byte[]> loaderCaptor = ArgumentCaptor.forClass(byte[].class);

        verify(loaderMock).loadData(loaderCaptor.capture());

        Assert.assertEquals(expectedLoadedData, new String(loaderCaptor.getValue()));
    }
}
