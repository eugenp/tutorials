package com.baeldung.data.complex;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.data.utility.Utility;
import com.baeldung.pojo.complex.AddressDetails;
import com.baeldung.pojo.complex.ContactDetails;
import com.baeldung.pojo.complex.CustomerAddressDetails;
import com.baeldung.pojo.complex.CustomerPortfolioComplex;

/**
 * 
 * This class is responsible for generating data for complex type object
 */

public class ComplexDataGeneration {

	public static CustomerPortfolioComplex generateData() {
		List<CustomerAddressDetails> customerAddressDetailsList = new ArrayList<CustomerAddressDetails>();
		for (int i = 0; i < 600000; i++) {
			CustomerAddressDetails customerAddressDetails = new CustomerAddressDetails();
			customerAddressDetails.setAge(20);
			customerAddressDetails.setFirstName(Utility.generateRandomName());
			customerAddressDetails.setLastName(Utility.generateRandomName());

			List<AddressDetails> addressDetailsList = new ArrayList<AddressDetails>();
			customerAddressDetails.setAddressDetails(addressDetailsList);

			for (int j = 0; j < 2; j++) {
				AddressDetails addressDetails = new AddressDetails();
				addressDetails.setZipcode(Utility.generateRandomZip());
				addressDetails.setAddress(Utility.generateRandomAddress());

				List<ContactDetails> contactDetailsList = new ArrayList<ContactDetails>();
				addressDetails.setContactDetails(contactDetailsList);

				for (int k = 0; k < 2; k++) {
					ContactDetails contactDetails = new ContactDetails();
					contactDetails.setLandline(Utility.generateRandomPhone());
					contactDetails.setMobile(Utility.generateRandomPhone());
					contactDetailsList.add(contactDetails);
					contactDetails = null;
				}

				addressDetailsList.add(addressDetails);
				addressDetails = null;
				contactDetailsList = null;
			}
			customerAddressDetailsList.add(customerAddressDetails);
			customerAddressDetails = null;

			if (i % 6000 == 0) {
				Runtime.getRuntime().gc();
			}
		}

		CustomerPortfolioComplex customerPortfolioComplex = new CustomerPortfolioComplex();
		customerPortfolioComplex.setCustomerAddressDetailsList(customerAddressDetailsList);
		customerAddressDetailsList = null;
		return customerPortfolioComplex;
	}
}
