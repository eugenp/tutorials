package com.baeldung.hexagonal.adaptors;

import java.util.Scanner;

import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.ports.ICustomerInfo;

public class CustomerInfoService implements ICustomerInfo {

    public Customer getCustomerInfoToUpdate(Scanner scanData) {
        System.out.println("Please enter Customer ID");
        int  customeID =Integer.parseInt(scanData.nextLine());
        System.out.println("Please enter Customer Name");
        String customerName = scanData.nextLine();
        System.out.println("Please enter Customer Address");
        String customerAddress = scanData.nextLine();
        System.out.println("Please enter Customer Email");
        String customerEmail = scanData.nextLine();
        System.out.println("Please enter Customer Phone Number");
        int customerPhone = Integer.parseInt(scanData.nextLine());
        
        Customer cust = new Customer(customeID, customerName, customerAddress, customerEmail, customerPhone);
        return cust;

    }

}
