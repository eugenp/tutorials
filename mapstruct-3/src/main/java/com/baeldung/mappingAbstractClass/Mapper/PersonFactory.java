package com.example.Mapper;

import com.example.entity.Customer;
import com.example.entity.Employee;

public class PersonFactory {

        public Employee createEmployee() { return new Employee(); }
        public Customer createCustomer() { return new Customer(); }
    }
    


