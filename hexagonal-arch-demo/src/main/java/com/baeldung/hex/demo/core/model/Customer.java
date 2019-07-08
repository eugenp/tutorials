package com.baeldung.hex.demo.core.model;

public class Customer {
        public String firstName;
        public String lastName;
        public String zipCode;
        public String emailId;
        public String ssn;

        // Standard getters and setters
        public boolean isCreditCheckRequired() {
                // Business logic that determines based on SSN
                return false;
        }

        public boolean isFreeShippingEligible() {
                // Business logic that determines free shipping based on zip code
                return false;
        }
}
