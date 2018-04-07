package com.baeldung.singletonbean;

import static org.junit.Assert.assertArrayEquals;

import java.util.List;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.singletonbean.CountryState;

public class CountryStateCacheBeanTest {

    private EJBContainer ejbContainer = null;

    private Context context = null;

    @Before
    public void init() {

        ejbContainer = EJBContainer.createEJBContainer();
        context = ejbContainer.getContext();
    }

    @Test
    public void whenCallGetStates_ReturnsStatesForCountry() throws Exception {

        String[] actualStates = { "Texas", "Alabama", "Alaska", "Arizona", "Arkansas" };

        CountryState countryStateBean = (CountryState) context.lookup("java:global/singleton-ejb-bean/CountryStateCacheBean");
        List<String> states = countryStateBean.getStates("UnitedStates");
        if (states != null) {
            assertArrayEquals(states.toArray(), actualStates);
        }
    }

    @After
    public void close() {
        if (ejbContainer != null)
            ejbContainer.close();
    }
}
