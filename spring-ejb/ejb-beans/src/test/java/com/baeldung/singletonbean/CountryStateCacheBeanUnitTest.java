package com.baeldung.singletonbean;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CountryStateCacheBeanUnitTest {

    private EJBContainer ejbContainer = null;

    private Context context = null;

    @Before
    public void init() {
        ejbContainer = EJBContainer.createEJBContainer();
        context = ejbContainer.getContext();
    }
    
    @Test
    public void whenCallGetStatesFromContainerManagedBean_ReturnsStatesForCountry() throws Exception {

        String[] expectedStates = { "Texas", "Alabama", "Alaska", "Arizona", "Arkansas" };

        CountryState countryStateBean = (CountryState) context.lookup("java:global/ejb-beans/CountryStateContainerManagedBean");
        List<String> actualStates = countryStateBean.getStates("UnitedStates");
        assertNotNull(actualStates);
        assertArrayEquals(expectedStates, actualStates.toArray());
    }
    
    @Test
    public void whenCallGetStatesFromBeanManagedBean_ReturnsStatesForCountry() throws Exception {

        String[] expectedStates = { "Texas", "Alabama", "Alaska", "Arizona", "Arkansas" };

        CountryState countryStateBean = (CountryState) context.lookup("java:global/ejb-beans/CountryStateBeanManagedBean");
        List<String> actualStates = countryStateBean.getStates("UnitedStates");
        assertNotNull(actualStates);
        assertArrayEquals(expectedStates, actualStates.toArray());
    }
    
    @Test
    public void whenCallSetStatesFromContainerManagedBean_SetsStatesForCountry() throws Exception {
        
        String[] expectedStates = { "California", "Florida", "Hawaii", "Pennsylvania", "Michigan" };
        
        CountryState countryStateBean = (CountryState) context.lookup("java:global/ejb-beans/CountryStateContainerManagedBean");
        countryStateBean.setStates("UnitedStates", Arrays.asList(expectedStates));
        
        List<String> actualStates = countryStateBean.getStates("UnitedStates");
        assertNotNull(actualStates);
        assertArrayEquals(expectedStates, actualStates.toArray());
    }
    
    @Test
    public void whenCallSetStatesFromBeanManagedBean_SetsStatesForCountry() throws Exception {
        
        String[] expectedStates = { "California", "Florida", "Hawaii", "Pennsylvania", "Michigan" };
        
        CountryState countryStateBean = (CountryState) context.lookup("java:global/ejb-beans/CountryStateBeanManagedBean");
        countryStateBean.setStates("UnitedStates", Arrays.asList(expectedStates));
        
        List<String> actualStates = countryStateBean.getStates("UnitedStates");
        assertNotNull(actualStates);
        assertArrayEquals(expectedStates, actualStates.toArray());
    }
    
    @After
    public void close() {
        if (ejbContainer != null)
            ejbContainer.close();
    }
}
