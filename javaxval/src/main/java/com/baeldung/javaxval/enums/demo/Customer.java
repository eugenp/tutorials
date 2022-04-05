package com.baeldung.javaxval.enums.demo;

import javax.validation.constraints.NotNull;

import com.baeldung.javaxval.enums.constraints.CustomerTypeSubset;
import com.baeldung.javaxval.enums.constraints.EnumNamePattern;
import com.baeldung.javaxval.enums.constraints.ValueOfEnum;

public class Customer {
    @ValueOfEnum(enumClass = CustomerType.class)
    private String customerTypeString;

    @NotNull
    @CustomerTypeSubset(anyOf = { CustomerType.NEW, CustomerType.OLD })
    private CustomerType customerTypeOfSubset;

    @EnumNamePattern(regexp = "NEW|DEFAULT")
    private CustomerType customerTypeMatchesPattern;

    public Customer() {
    }

    public Customer(String customerTypeString, CustomerType customerTypeOfSubset, CustomerType customerTypeMatchesPattern) {
        this.customerTypeString = customerTypeString;
        this.customerTypeOfSubset = customerTypeOfSubset;
        this.customerTypeMatchesPattern = customerTypeMatchesPattern;
    }

    public String getCustomerTypeString() {
        return customerTypeString;
    }

    public void setCustomerTypeString(String customerTypeString) {
        this.customerTypeString = customerTypeString;
    }

    public CustomerType getCustomerTypeOfSubset() {
        return customerTypeOfSubset;
    }

    public void setCustomerTypeOfSubset(CustomerType customerTypeOfSubset) {
        this.customerTypeOfSubset = customerTypeOfSubset;
    }

    public CustomerType getCustomerTypeMatchesPattern() {
        return customerTypeMatchesPattern;
    }

    public void setCustomerTypeMatchesPattern(CustomerType customerTypeMatchesPattern) {
        this.customerTypeMatchesPattern = customerTypeMatchesPattern;
    }

    public static class Builder {
        private String customerTypeString;
        private CustomerType customerTypeOfSubset = CustomerType.NEW;
        private CustomerType customerTypeMatchesPattern;

        public Builder withCustomerTypeString(String customerTypeString) {
            this.customerTypeString = customerTypeString;
            return this;
        }

        public Builder withCustomerTypeOfSubset(CustomerType customerTypeOfSubset) {
            this.customerTypeOfSubset = customerTypeOfSubset;
            return this;
        }

        public Builder withCustomerTypeMatchesPattern(CustomerType customerTypeMatchesPattern) {
            this.customerTypeMatchesPattern = customerTypeMatchesPattern;
            return this;
        }

        public Customer build() {
            return new Customer(customerTypeString, customerTypeOfSubset, customerTypeMatchesPattern);
        }
    }
}
