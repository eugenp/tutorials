package edu.baeldung.hexagonalarchitecture;

import edu.baeldung.hexagonalarchitecture.mock.AppMock;
import edu.baeldung.hexagonalarchitecture.pojo.Customer;
import org.junit.Assert;
import org.junit.Test;

public class AppTest {

    @Test
    public void whenCreateCustomer_thenGetPreditableIdentificationFromMock(){
        AppMock mock = new AppMock();

        Customer predictable = mock.createCustomer(new Customer());

        Assert.assertTrue("Pred".equalsIgnoreCase(predictable.getIdentification()));
    }

    @Test
    public void whenCreateCustomer_thenGetPreditableFullNameFromMock(){
        AppMock mock = new AppMock();

        Customer predictable = mock.createCustomer(new Customer());

        Assert.assertTrue("This is predictable".equalsIgnoreCase(predictable.getFullName()));
    }
}
