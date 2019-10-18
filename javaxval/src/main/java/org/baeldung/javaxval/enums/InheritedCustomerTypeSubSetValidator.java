package org.baeldung.javaxval.enums;

import org.baeldung.javaxval.enums.constraints.CustomerTypeSubset;
import org.baeldung.javaxval.enums.demo.CustomerType;

public class InheritedCustomerTypeSubSetValidator extends EnumSubSetValidator<CustomerTypeSubset, CustomerType> {
    @Override
    public void initialize(CustomerTypeSubset constraint) {
        super.initialize(constraint.anyOf());
    }
}
