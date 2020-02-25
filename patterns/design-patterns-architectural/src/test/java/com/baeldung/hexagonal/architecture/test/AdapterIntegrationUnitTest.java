package com.baeldung.hexagonal.architecture.test;

import static org.junit.Assert.*;

import com.baeldung.hexagonal.architecture.adapters.BreweryServiceAdapter;
import com.baeldung.hexagonal.architecture.adapters.HttpClientAdapter;
import com.baeldung.hexagonal.architecture.core.Brewery;
import com.baeldung.hexagonal.architecture.ports.BreweryPort;
import com.baeldung.hexagonal.architecture.ports.HttpPort;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class AdapterIntegrationUnitTest {

    private static HttpPort httpClient;
    private static BreweryPort breweryServiceAdapter;

    @BeforeClass
    public static void setUpUserDaoInstance() {
        httpClient = new HttpClientAdapter();
        breweryServiceAdapter = new BreweryServiceAdapter(httpClient);
    }

    @Test
    public void whenSearchCalledForExistentCity_thenSuccessful() throws Exception{
        List<Brewery> breweryList = breweryServiceAdapter.search("sand&diego");

        assertEquals(breweryList.size()>1, true);
    }

    @Test
    public void whenSearchCalledForNonExistentCity_thenEmpty() throws Exception{
        List<Brewery> breweryList = breweryServiceAdapter.search("nogoya");

        assertEquals(breweryList.size()>1, false);
    }
}
