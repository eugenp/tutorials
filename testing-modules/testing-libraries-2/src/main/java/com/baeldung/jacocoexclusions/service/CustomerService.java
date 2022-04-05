package com.baeldung.jacocoexclusions.service;

import com.baeldung.jacocoexclusions.generated.Generated;

public class CustomerService {

    //this method will be excluded from coverage due to @Generated.
    @Generated
    public String getProductId() {
        return "An ID";
    }

    public String getCustomerName() {
        return "some name";
    }
}
