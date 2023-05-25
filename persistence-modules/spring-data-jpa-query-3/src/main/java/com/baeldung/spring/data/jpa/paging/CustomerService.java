package com.baeldung.spring.data.jpa.paging;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private final CustomerRepository customerRepository;

    public Page<Customer> getCustomers(int page, int size) {

        Pageable pageRequest = createPageRequestUsing(page, size);

        List<Customer> allCustomers = customerRepository.findAll();
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allCustomers.size());

        List<Customer> pageContent = allCustomers.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, allCustomers.size());
    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }
}
