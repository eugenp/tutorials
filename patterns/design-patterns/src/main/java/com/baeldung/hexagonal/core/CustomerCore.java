package com.baeldung.hexagonal.core;

import java.util.Properties;
import java.util.Scanner;

import com.baeldung.hexagonal.ports.ICustomerInfo;
import com.baeldung.hexagonal.ports.IDatabaseOperation;
import com.baeldung.hexagonal.ports.ILogMessage;
import com.baeldung.hexagonal.ports.INotification;

public class CustomerCore {

	public INotification notification = null;
	public IDatabaseOperation dBOperation = null;
	public ILogMessage logMessage = null;
	public ICustomerInfo customerInfo = null;

	public CustomerCore(
			INotification notification, 
			IDatabaseOperation dBOperation, 
			ILogMessage logMessage,
			ICustomerInfo custInfo) 
	{
		super();
		this.notification = notification;
		this.dBOperation = dBOperation;
		this.logMessage = logMessage;
		this.customerInfo = custInfo;
	}

    public boolean sendEmail(int customerID) {
        Properties porperties = System.getProperties();
        CustomMessage customMessage = new CustomMessage();
        // set message content

        Customer customer = dBOperation.findCustomer(customerID);

        if (customer == null)
            return Boolean.FALSE;

        notification.sendNotificatin(porperties, customer, customMessage);
        logMessage(1, "Email Sent successfully");

        return Boolean.TRUE;
    }

    public boolean addCustomerData(Customer customer) {
        logMessage(1, "Customer added Successfully");
        return dBOperation.AddCustomer(customer);
    }

    public boolean updateCustomerInformation() {

        Customer customerToUpdate = customerInfo.getCustomerInfoToUpdate(new Scanner(System.in));
        boolean status  = dBOperation.updateCustomerData(customerToUpdate.getCustomerID(), customerToUpdate);
        logMessage(1, "Customer updated Successfully");
        
        return status;
    }

    public boolean logMessage(int severity, String message) {
        logMessage.logMessage(1, message);
        return Boolean.TRUE;
    }

}
