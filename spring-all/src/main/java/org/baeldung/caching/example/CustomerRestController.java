package org.baeldung.caching.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cachetest")
public class CustomerRestController {

    @Autowired
    private CustomerServiceWithParent customerServiceWithParent;

    @RequestMapping(method = RequestMethod.GET)
    public String getAddress4() {

        Customer customer = new Customer();
        customer.setAddress("Test address");

        return customerServiceWithParent.getAddress4(customer);
    }

}
