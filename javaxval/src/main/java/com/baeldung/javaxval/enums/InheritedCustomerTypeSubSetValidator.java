package com.baeldung.javaxval.enums;

import com.baeldung.javaxval.enums.constraints.CustomerTypeSubset;
import com.baeldung.javaxval.enums.demo.CustomerType;

public class InheritedCustomerTypeSubSetValidator extends EnumSubSetValidator<CustomerTypeSubset, CustomerType> {
    @Override
    public void initialize(CustomerTypeSubset constraint) {
        super.initialize(constraint.anyOf());
    }
}
