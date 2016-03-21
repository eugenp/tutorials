package org.baeldung.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="customers")
public class CustomerListRepresentation
{
    private List<Customer> customers;

    public CustomerListRepresentation() {
    }

    public CustomerListRepresentation(final List<Customer> customers) {
        super();
        this.customers = customers;
    }

    @XmlElement(name="customer")
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(final List<Customer> customers) {
        this.customers = customers;
    }


}
