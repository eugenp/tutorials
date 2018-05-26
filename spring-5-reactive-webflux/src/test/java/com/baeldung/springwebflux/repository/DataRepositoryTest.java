package com.baeldung.springwebflux.repository;

import com.baeldung.springwebflux.model.Equity;
import org.junit.Assert;
import org.junit.Test;

public class DataRepositoryTest {

    @Test
    public void testGenerateEquityPriceChanges() {
        Equity equity = DataRepository.generateEquityPriceChanges();
        Assert.assertTrue(equity != null);
        Assert.assertTrue(equity.getEquityId() > -1 && equity.getEquityPrice() > -1);
    }
}