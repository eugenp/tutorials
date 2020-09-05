package com.baeldung.jsonoptimization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerShortNamesNoNull extends CustomerShortNames {
    
    
    @Override
    public String toString() {
        return "CustomerShortNamesNoNull [toString()=" + super.toString() + "]";
    }
    
    public static CustomerShortNamesNoNull[] fromCustomers(Customer[] customers) {
        CustomerShortNamesNoNull[] feedback = new CustomerShortNamesNoNull[customers.length];   
            
        for(int i = 0; i < customers.length; i++) {
            Customer aCustomer = customers[i];
            CustomerShortNamesNoNull newOne = new CustomerShortNamesNoNull();
            
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
