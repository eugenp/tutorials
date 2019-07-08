package com.baeldung.hex.demo.adapter.secondary.persistence.translator;

import com.baeldung.hex.demo.adapter.secondary.persistence.entity.CustomerEntity;
import com.baeldung.hex.demo.core.model.Customer;

public class CustomerEntityAdapter {
        public Customer toDomain(CustomerEntity entity) {
                return new Customer();
        }

        public CustomerEntity toEntity(Customer domain) {
                return new CustomerEntity();
        }
}
