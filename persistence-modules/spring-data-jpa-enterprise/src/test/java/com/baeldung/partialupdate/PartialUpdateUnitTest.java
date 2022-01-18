package com.baeldung.partialupdate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.partialupdate.model.Customer;
import com.baeldung.partialupdate.model.CustomerDto;
import com.baeldung.partialupdate.model.CustomerStructured;
import com.baeldung.partialupdate.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PartialUpdateApplication.class)
public class PartialUpdateUnitTest {

    @Autowired
    CustomerService service;

	@Test
    public void givenCustomer_whenUpdate_thenSuccess() {
        Customer myCustomer = service.addCustomer("John");
        myCustomer = service.updateCustomer(myCustomer.id, "+00");
        assertEquals("+00", myCustomer.phone);
    }

    @Test
    public void givenCustomer_whenUpdateWithQuery_thenSuccess() {
        Customer myCustomer = service.addCustomer("John");
        service.updateCustomerWithCustomQuery(myCustomer.id, "+88");
        myCustomer = service.getCustomer(myCustomer.id);
        assertEquals("+88", myCustomer.phone);
    }

    @Test
    public void givenCustomerDto_whenUpdateWithMapper_thenSuccess() {
        CustomerDto dto = new CustomerDto(new Customer());
        dto.name = "Johnny";
        Customer entity = service.addCustomer(dto);

        CustomerDto dto2 = new CustomerDto(entity.id);
        dto2.phone = "+44";
        entity = service.updateCustomer(dto2);

        assertEquals("Johnny", entity.name);
    }

    @Test
    public void givenCustomerStructured_whenUpdateCustomerPhone_thenSuccess() {
        CustomerStructured myCustomer = service.addCustomerStructured("John");
        assertEquals(null, myCustomer.contactPhones);

        service.addCustomerPhone(myCustomer.id, "+44");
        myCustomer = service.updateCustomerStructured(myCustomer.id, "Mr. John");

        assertNotEquals(null, myCustomer.contactPhones);
        assertEquals(1, myCustomer.contactPhones.size());
    }
}
