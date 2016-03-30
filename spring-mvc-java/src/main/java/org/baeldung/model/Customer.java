package org.baeldung.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;

public class Customer extends ResourceSupport
{
    private String customerId;
    private String customerName;
    private String companyName;

    public Customer() {
        super();
    }

    public Customer(final String customerId, final String customerName, final String companyName) {
        super();
        this.customerId = customerId;
        this.customerName = customerName;
        this.companyName = companyName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCompany() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

}
