package com.baeldung.jsonoptimization;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerNoNull extends Customer {

    @Override
    public String toString() {
        return "CustomerNoNull [toString()=" + super.toString() + "]";
    }

    public static CustomerNoNull[] fromCustomers(Customer[] customers) {
        CustomerNoNull[] feedback = new CustomerNoNull[customers.length];

        for (int i = 0; i < customers.length; i++) {
            Customer aCustomer = customers[i];
            CustomerNoNull newOne = new CustomerNoNull();

            newOne.setId(aCustomer.getId());
            newOne.setFirstName(aCustomer.getFirstName());
            newOne.setLastName(aCustomer.getLastName());
            newOne.setStreet(aCustomer.getStreet());
            newOne.setCity(aCustomer.getCity());
            newOne.setPostalCode(aCustomer.getPostalCode());
            newOne.setState(aCustomer.getState());
            newOne.setPhoneNumber(aCustomer.getPhoneNumber());
            newOne.setEmail(aCustomer.getEmail());

            feedback[i] = newOne;
        }

        return feedback;
    }

}
