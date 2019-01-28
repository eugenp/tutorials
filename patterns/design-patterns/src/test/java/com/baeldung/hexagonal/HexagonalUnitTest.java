package com.baeldung.hexagonal;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.hexagonal.adaptors.CustomerInfoService;
import com.baeldung.hexagonal.adaptors.DatabaseOperation;
import com.baeldung.hexagonal.adaptors.EmailNotificationService;
import com.baeldung.hexagonal.adaptors.PushMessageNotificationService;
import com.baeldung.hexagonal.adaptors.LoggingOperation;
import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.core.CustomerCore;

public class HexagonalUnitTest{
    @Test
    public void AppTest(){
        CustomerCore cusCore =new CustomerCore(new EmailNotificationService(),new DatabaseOperation(),new LoggingOperation(),new CustomerInfoService());
       // assertEquals(Boolean.TRUE,cusCore.updateCustomerInformation());  
        assertEquals(Boolean.TRUE,cusCore.addCustomerData(new Customer(123, "TestCustomer", "XXX", "abc@xx.com", 012344545455)));
        assertEquals(Boolean.TRUE,cusCore.sendEmail(123));
    }

}
