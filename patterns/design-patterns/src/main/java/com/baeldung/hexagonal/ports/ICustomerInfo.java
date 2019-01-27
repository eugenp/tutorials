package com.baeldung.hexagonal.ports;

import java.util.Scanner;

import com.baeldung.hexagonal.core.Customer;

public interface ICustomerInfo {
    public Customer getCustomerInfoToUpdate(Scanner scanData);

}
