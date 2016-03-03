package org.baeldung.data.simple;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.data.utility.Utility;
import org.baeldung.pojo.simple.Customer;
import org.baeldung.pojo.simple.CustomerPortfolioSimple;

/**
 * 
 * This class is responsible for generating data for simple type object
 */

public class SimpleDataGeneration {

	public static CustomerPortfolioSimple generateData() {
		List<Customer> customerList = new ArrayList<Customer>();
		for (int i = 0; i < 1; i++) {
			Customer customer = new Customer();
			customer.setAge(20);
			customer.setFirstName(Utility.generateRandomName());
			customer.setLastName(Utility.generateRandomName());

			customerList.add(customer);
			customer = null;

		}

		CustomerPortfolioSimple customerPortfolioSimple = new CustomerPortfolioSimple();
		customerPortfolioSimple.setCustomerLists(customerList);
		customerList = null;
		return customerPortfolioSimple;
	}
}
