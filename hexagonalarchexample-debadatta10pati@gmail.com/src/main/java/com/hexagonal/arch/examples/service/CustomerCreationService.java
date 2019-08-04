package com.hexagonal.arch.examples.service;

import com.hexagonal.arch.examples.core.domain.CustomerCriteria;

public class CustomerCreationService {
    
    public String createCustomerId(CustomerCriteria criteria) {

        return (criteria.getBirthDate()+"-"+criteria.getFirstName()+"-"+criteria.getLastName()+"-"+criteria.getLastFourDigitsOfSSN());
   }

}
